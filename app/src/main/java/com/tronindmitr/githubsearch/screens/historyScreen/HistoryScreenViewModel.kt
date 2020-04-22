package com.tronindmitr.githubsearch.screens.historyScreen

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.tronindmitr.githubsearch.database.RepositoryDatabaseDao
import com.tronindmitr.githubsearch.util.RepositoryItem
import com.tronindmitr.githubsearch.util.addOrUpdateDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HistoryScreenViewModel(val database: RepositoryDatabaseDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private var coroutineScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    //private var listRepositoryItems = database.getAllRepositoryItems()

    public var isFiltered : MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    init{
        isFiltered.value = false
    }

    val _list : LiveData<List<RepositoryItem>> =
        Transformations.switchMap(isFiltered) {
        if (it == true) {
            Log.e("HIstory", "fav items changed")
            database.getFavRepositoryItems()}
        else {
            Log.e("HIstory", "all items changed")
            database.getAllRepositoryItems()
        }
    }


//    private var _list = database.getAllRepositoryItems()
    val list: LiveData<List<RepositoryItem>>
        get() = _list

    fun onClickSortByAll() {
        Log.e("History screen", "All")
        isFiltered.value = false
    }

    fun onClickSortByFav() {
        Log.e("History screen", "All")
        isFiltered.value = true

    }





    fun onItemBrowse(repositoryItem: RepositoryItem) {
        coroutineScope.launch {
            update(repositoryItem)
        }

    }

    suspend fun update(repositoryItem: RepositoryItem) {
        database.update( RepositoryItem(
            repositoryItem.id,
            repositoryItem.name,
            repositoryItem.owner,
            repositoryItem.url,
            repositoryItem.description,
            repositoryItem.language,
            repositoryItem.createDate,
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date()),
            false)
        )
    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}