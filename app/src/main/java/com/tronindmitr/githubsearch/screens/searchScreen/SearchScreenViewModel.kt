package com.tronindmitr.githubsearch.screens.searchScreen

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tronindmitr.githubsearch.RepositoryItem
import com.tronindmitr.githubsearch.ResponseData
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

enum class RepositorySearchApiStatus { LOADING, EMPTY, ERROR, DONE }

enum class RepositorySearchApiResponse { NOTEMPTY, }


class SearchScreenViewModel : ViewModel() {

    private var viewmodelJob = Job()
    private var couroutineScope = CoroutineScope(viewmodelJob + Dispatchers.Main)

    private val _response = MutableLiveData<List<RepositoryItem>>()
    val response: LiveData<List<RepositoryItem>>
        get() = _response

    private val _status = MutableLiveData<RepositorySearchApiStatus>()
    val status: LiveData<RepositorySearchApiStatus>
        get() = _status


    init {
    }

    fun onClick(string: String) {
        _response.value = ArrayList()
        _status.value = RepositorySearchApiStatus.LOADING
        couroutineScope.launch {
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
        couroutineScope.cancel()
    }
}