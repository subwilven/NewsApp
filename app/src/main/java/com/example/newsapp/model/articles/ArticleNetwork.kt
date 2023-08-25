package com.example.newsapp.model.articles

import com.google.gson.annotations.SerializedName
import java.util.Date


data class ArticleNetwork(

    @SerializedName("author")
    val author: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("url")
    val articleUrl: String?,

    @SerializedName("urlToImage")
    var imageUrl: String?,

    @SerializedName("publishedAt")
    val publishedAt: Date?,

    @SerializedName("content")
    val content: String?,

    )


fun ArticleNetwork.asEntityModel() = ArticleEntity(
    0,
    author,
    title.toString(),
    description,
    articleUrl,
    imageUrl,
    publishedAt,
    content,
    false
)