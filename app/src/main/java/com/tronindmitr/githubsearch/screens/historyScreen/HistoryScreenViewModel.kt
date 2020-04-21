package com.tronindmitr.githubsearch.screens.historyScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tronindmitr.githubsearch.screens.database.RepositoryDatabaseDao
import com.tronindmitr.githubsearch.screens.util.RepositoryItem
import com.tronindmitr.githubsearch.screens.util.addOrUpdateDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HistoryScreenViewModel(val database: RepositoryDatabaseDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private var coroutineScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private var listRepositoryItems = database.getAllRepositoryItems()

    private val _list = database.getAllRepositoryItems()
    val list: LiveData<List<RepositoryItem>>
        get() = _list


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
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date()))
        )
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}