package com.tronindmitr.githubsearch.screens.searchScreen

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tronindmitr.githubsearch.RepositoryItem

@Dao
interface RepositoryDatabaseDao {
    @Insert
    fun insert(repositoryItem: RepositoryItem)

    @Update
    fun update(repositoryItem: RepositoryItem)

    @Query("SELECT * from repository_table WHERE id = :key")
    fun get(key: Long) : RepositoryItem

    @Query("SELECT * FROM repository_table ORDER BY repository_update_date")
    fun getAllRepositoryItems(): LiveData<List<RepositoryItem>>
}