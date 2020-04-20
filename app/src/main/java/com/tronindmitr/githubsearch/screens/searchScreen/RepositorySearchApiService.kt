package com.tronindmitr.githubsearch.screens.searchScreen

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tronindmitr.githubsearch.RepositoryItem
import com.tronindmitr.githubsearch.ResponseData
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.github.com/search/"

private  val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface RepositorySearchApiService {
    @GET ("repositories")
    fun getPropities(@Query("q") address: CharSequence
    ) : Call<ResponseData>

    @GET ("repositories")
    suspend fun getProp(@Query("q") address: CharSequence) : Response<ResponseData>
}

object RepositorySearchApi {
    val retrofitService : RepositorySearchApiService by lazy {
        retrofit.create(RepositorySearchApiService::class.java)
    }
}
