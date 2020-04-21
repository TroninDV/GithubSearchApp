package com.tronindmitr.githubsearch.screens.util

import android.util.Log
import com.tronindmitr.githubsearch.screens.database.RepositoryDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

fun addOrUpdateDatabase (database: RepositoryDatabaseDao, corotineScope: CoroutineScope, repositoryItem: RepositoryItem) {
    corotineScope.launch {
        Log.e("Database add","is working");
        val itemInDatabase = database.get(repositoryItem.id)
        if (itemInDatabase == null)
            database.insert(
                RepositoryItem(
                    repositoryItem.id,
                    repositoryItem.name,
                    repositoryItem.owner,
                    repositoryItem.url,
                    repositoryItem.description,
                    repositoryItem.language,
                    repositoryItem.createDate,
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(System.currentTimeMillis()).toString()
                )
            )
        else
            database.update(
                RepositoryItem(
                    repositoryItem.id,
                    repositoryItem.name,
                    repositoryItem.owner,
                    repositoryItem.url,
                    repositoryItem.description,
                    repositoryItem.language,
                    repositoryItem.createDate,
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date())
                )
            )
    }
}