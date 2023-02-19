package com.example.newsapp.model.articles

data class Article(
    val id: Int,
    val author: String?,
    val title: String,
    val description: String?,
    val articleUrl: String?,
    val imageUrl: String?,
    val publishedAt: String?,
    val content: String?,
    val isFavorite :Boolean
)
