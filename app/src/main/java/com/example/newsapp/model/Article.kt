package com.example.newsapp.model

import com.google.gson.annotations.SerializedName

data class Article(

    @SerializedName("author")
    val author: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("url")
    val articleUrl: String,
    @SerializedName("urlToImage")
    var imageUrl: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("content")
    val content: String

)
