package com.tronindmitr.githubsearch.screens.searchScreen

import android.util.Log
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

class SearchScreenViewModel : ViewModel() {

    private var viewmodelJob = Job()
    private var couroutineScope = CoroutineScope(viewmodelJob + Dispatchers.Main)

    private val _response = MutableLiveData<List<RepositoryItem>>()
    val response : LiveData<List<RepositoryItem>>
            get() = _response

    private val _status = MutableLiveData<String>()
    val status : LiveData<String>
        get() = _status



    init {
    }

    fun onClick(string: CharSequence) {

        couroutineScope.launch {
            try {
            val serverResponse = RepositorySearchApi.retrofitService.getProp(string)
                if (serverResponse.isSuccessful) {
                    val str = serverResponse.body()?.items?.get(0)?.language ?: "null"
                    _status.value = "Success"
                } else {
                    Log.e("HTTPS search:","HTTPS return code is" + serverResponse.code())
                }

            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
                Log.e("HTTPS search", e.message ?: "")
            }
        }



//        RepositorySearchApi.retrofitService.getPropities(string).enqueue(object : Callback<ResponseData>  {
//
//            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
//                _response.value = "Failure" + t.message
//                Log.e("HTTPS search", t.message ?: "")
//            }
//
//            override fun onResponse(
//                call: Call<ResponseData>,
//                response: Response<ResponseData>
//            ) {
//                val str = response.body()?.items?.get(0)?.language ?: "null"
//                _response.value = "Success: $str"
//            }
//
//        })
    }

    override fun onCleared() {
        super.onCleared()
        couroutineScope.cancel()
    }
}