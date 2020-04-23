package com.tronindmitr.githubsearch.screens.historyScreen

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.tronindmitr.githubsearch.database.RepositoryDatabaseDao
import com.tronindmitr.githubsearch.util.RepositoryItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HistoryScreenViewModel(val database: RepositoryDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()

    private var coroutineScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private var _isFiltered: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val isFiltered: LiveData<Boolean>
        get() = _isFiltered


    private val _list: LiveData<List<RepositoryItem>> =
        Transformations.switchMap(_isFiltered) {
            if (it == true) {
                database.getFavRepositoryItems()
            } else {
                database.getAllRepositoryItems()
            }
        }

    val list: LiveData<List<RepositoryItem>>
        get() = this._list

    fun onClickDeleteAll() {
        coroutineScope.launch {
            database.deleteAllREpositoryItems()
        }
    }

    fun onClickSortByAll() {
        Log.e("History screen", "All")
        _isFiltered.postValue(false)
    }

    fun onClickSortByFav() {
        Log.e("History screen", "All")
        _isFiltered.postValue(true)
    }

    fun onItemBrowse(repositoryItem: RepositoryItem) {
        coroutineScope.launch {
            database.update(
                RepositoryItem(
                    repositoryItem.id,
                    repositoryItem.name,
                    repositoryItem.owner,
                    repositoryItem.url,
                    repositoryItem.description,
                    repositoryItem.language,
                    repositoryItem.createDate,
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date()),
                    repositoryItem.favor
                )
            )
        }
    }

    fun onClickFav(repositoryItem: RepositoryItem) {
        coroutineScope.launch {
                database.update(
                    RepositoryItem(
                        repositoryItem.id,
                        repositoryItem.name,
                        repositoryItem.owner,
                        repositoryItem.url,
                        repositoryItem.description,
                        repositoryItem.language,
                        repositoryItem.createDate,
                        repositoryItem.updateDate,
                        !repositoryItem.favor
                    )
                )
        }
    }

    fun gtFiltered(): Boolean {
        return isFiltered.value ?: false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}