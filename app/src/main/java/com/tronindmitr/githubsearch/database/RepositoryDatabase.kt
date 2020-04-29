package com.tronindmitr.githubsearch.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.tronindmitr.githubsearch.util.OwnerConverter
import com.tronindmitr.githubsearch.util.RepositoryItem


@Database(entities = [RepositoryItem::class], version = 2, exportSchema = false)
@TypeConverters(OwnerConverter::class)
abstract class RepositoryDatabase : RoomDatabase() {

    abstract val repositoryDatabaseDao: RepositoryDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: RepositoryDatabase? = null

        fun getInstance(context: Context): RepositoryDatabase {
            synchronized(this) {
                var instance =
                    INSTANCE

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



@Dao
interface RepositoryDatabaseDao {
    @Insert
    fun insert(repositoryItem: RepositoryItem)

    @Update
    fun update(repositoryItem: RepositoryItem)

    @Query("SELECT * from repository_table WHERE id = :key")
    fun get(key: Long): RepositoryItem

    //@Query("SELECT * FROM repository_table WHERE repository_favor = 1")
    @Query("SELECT * FROM repository_table ORDER BY repository_update_date DESC")
    fun getAllRepositoryItems(): LiveData<List<RepositoryItem>>

    @Query("SELECT * FROM repository_table WHERE repository_favor = 1 ORDER BY repository_update_date DESC")
    fun getFavRepositoryItems(): LiveData<List<RepositoryItem>>
    
    @Query("DELETE FROM repository_table")
    fun deleteAllREpositoryItems()
}