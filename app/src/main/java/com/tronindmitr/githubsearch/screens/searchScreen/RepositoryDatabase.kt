package com.tronindmitr.githubsearch.screens.searchScreen

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tronindmitr.githubsearch.RepositoryItem

@Database(entities = [RepositoryItem::class], version = 1, exportSchema = false)
abstract class RepositoryDatabase : RoomDatabase() {

    abstract val repositoryDatabaseDao: RepositoryDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: RepositoryDatabase? = null

        fun getInstance(context: Context) : RepositoryDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RepositoryDatabase::class.java,
                        "repository_table"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}