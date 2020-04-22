package com.tronindmitr.githubsearch.screens.historyScreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tronindmitr.githubsearch.database.RepositoryDatabaseDao
import java.lang.IllegalArgumentException

class HistoryScreenViewModelFactory (
    private val dataSource: RepositoryDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryScreenViewModel::class.java))
            return HistoryScreenViewModel(dataSource, application) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
