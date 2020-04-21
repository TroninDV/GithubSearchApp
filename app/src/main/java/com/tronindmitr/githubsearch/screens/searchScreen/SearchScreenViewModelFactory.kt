package com.tronindmitr.githubsearch.screens.searchScreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tronindmitr.githubsearch.screens.database.RepositoryDatabaseDao

class SearchScreenViewModelFactory (
    private val dataSource: RepositoryDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchScreenViewModel::class.java))
            return SearchScreenViewModel(dataSource, application) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}