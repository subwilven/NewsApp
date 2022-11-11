package com.example.newsapp.model.articles

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "author")
    @SerializedName("author")
    val author: String?,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description: String?,

    @ColumnInfo(name = "article_url")
    @SerializedName("url")
    val articleUrl: String?,

    @ColumnInfo(name = "image_url")
    @SerializedName("urlToImage")
    var imageUrl: String?,

    @ColumnInfo(name = "publishedAt")
    @SerializedName("publishedAt")
    val publishedAt: String?,

    @ColumnInfo(name = "content")
    @SerializedName("content")
    val content: String?

)
