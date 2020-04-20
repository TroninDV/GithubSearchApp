package com.tronindmitr.githubsearch

import com.squareup.moshi.Json

data class RepositoryItem(
    val id: String,
    val name: String,
    @Json(name = "html_url") val url: String,
    val description: String?,
    val language: String?,
    @Json(name = "created_at") val date: String
)

data class ResponseData(
    val total_count: String,
    //val incomplete_results: Boolean,
    val items: List<RepositoryItem>
)