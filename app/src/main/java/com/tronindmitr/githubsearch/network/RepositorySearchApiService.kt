package com.tronindmitr.githubsearch.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tronindmitr.githubsearch.util.ResponseData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.github.com/search/"

private  val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface RepositorySearchApiService {

    @GET ("repositories")
    suspend fun getProp(@Query("q") word: String, @Query("per_page") per_page: Int ) : Response<ResponseData>
}

object RepositorySearchApi {
    val retrofitService : RepositorySearchApiService by lazy {
        retrofit.create(
            RepositorySearchApiService::class.java)
    }
}
