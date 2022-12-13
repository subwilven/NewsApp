package com.example.newsapp.model.articles

import com.example.newsapp.util.convertToAgoTime

data class ArticleUi(
    val id: Int,
    val author: String?,
    val title: String,
    val description: String?,
    val articleUrl: String?,
    val imageUrl: String?,
    val publishedAt: String?,
    val content: String?,
    val isFavorite :Boolean
) {

    constructor(article: Article) : this(
        article.id,
        article.author,
        article.title,
        article.description,
        article.articleUrl,
        article.imageUrl,
        article.publishedAt?.convertToAgoTime(),
        article.content,
        article.isFavorite
    )

}
