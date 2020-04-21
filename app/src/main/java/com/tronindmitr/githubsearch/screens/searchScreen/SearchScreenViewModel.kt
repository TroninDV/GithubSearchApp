package com.tronindmitr.githubsearch.screens.searchScreen

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tronindmitr.githubsearch.screens.database.RepositoryDatabase
import com.tronindmitr.githubsearch.screens.database.RepositoryDatabaseDao
import com.tronindmitr.githubsearch.screens.network.RepositorySearchApi
import com.tronindmitr.githubsearch.screens.util.RepositoryItem
import com.tronindmitr.githubsearch.screens.util.addOrUpdateDatabase
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

enum class RepositorySearchApiStatus { LOADING, EMPTY, ERROR, DONE }

enum class RepositorySearchApiResponse { NOTEMPTY, }


class SearchScreenViewModel(val database: RepositoryDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private var viewmodelJob = Job()
    private var mainCouroutineScope = CoroutineScope(viewmodelJob + Dispatchers.Main)
    private var ioCoroutineScope = CoroutineScope(viewmodelJob + Dispatchers.IO)

    private val _response = MutableLiveData<List<RepositoryItem>>()
    val response: LiveData<List<RepositoryItem>>
        get() = _response

    private val _status = MutableLiveData<RepositorySearchApiStatus>()
    val status: LiveData<RepositorySearchApiStatus>
        get() = _status


    init {
    }

    fun onItemBrowse(repositoryItem: RepositoryItem) {
        addOrUpdateDatabase(database, ioCoroutineScope, repositoryItem)
    }

    fun onClick(string: String) {
        _response.value = ArrayList()
        _status.value = RepositorySearchApiStatus.LOADING
        mainCouroutineScope.launch {
            try {
                val str = string.replace(' ', '+') + "+sort:starts"
                val serverResponse = RepositorySearchApi.retrofitService.getProp(string, 100)

                if (serverResponse.isSuccessful) {
                    val responseBody = serverResponse.body()
                    if (responseBody != null) {
                        if (responseBody.items.isEmpty()) {
                            _status.value = RepositorySearchApiStatus.EMPTY
                        } else {
                            _status.value = RepositorySearchApiStatus.DONE
                            _response.value = responseBody.items
                        }
                    }

                } else {
                    _status.value = RepositorySearchApiStatus.ERROR
                    _response.value = ArrayList()
                    Log.e("HTTPS search:", "HTTPS return code is" + serverResponse.code())
                }

            } catch (e: Exception) {
                _status.value = RepositorySearchApiStatus.ERROR
                _response.value = ArrayList()
                Log.e("HTTPS search", e.message ?: "")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewmodelJob.cancel()
    }


}


