package com.tronindmitr.githubsearch

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


@Entity(tableName = "repository_table")
data class RepositoryItem(

    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "repository_name")
    val name: String,

    @ColumnInfo(name = "owner")
    val owner: Owner,

    @ColumnInfo(name = "repository_url")
    @Json(name = "html_url") val url: String,

    @ColumnInfo(name = "repository_description")
    val description: String?,

    @ColumnInfo(name = "repository_language")
    val language: String?,
    @ColumnInfo(name = "repository_create_date")
    @Json(name = "created_at") val createDate: String,

    @ColumnInfo(name = "repository_update_date")
    @Json(name = "updated_at")  val updateDate: String

)

data class ResponseData(
    val total_count: String,
    //val incomplete_results: Boolean,
    val items: List<RepositoryItem>
)

data class Owner (
    val login: String
)

