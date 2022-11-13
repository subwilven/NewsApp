package com.example.newsapp.model.articles

import androidx.room.Entity
import com.example.newsapp.util.convertToAgoTime

@Entity(tableName = "article")
data class ArticleUi(
    val id: Int,
    val author: String?,
    val title: String,
    val description: String?,
    val articleUrl: String?,
    val imageUrl: String?,
    val publishedAt: String?,
    val content: String?
) {

    constructor(article: Article) : this(
        article.id,
        article.author,
        article.title,
        article.description,
        article.articleUrl,
        article.imageUrl,
        article.publishedAt?.convertToAgoTime(),
        article.content
    )

}
