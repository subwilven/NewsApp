package com.example.newsapp.data.articles.data_source.local

import com.example.newsapp.data.articles.data_source.ArticlesDataSource
import com.example.newsapp.model.Article
import javax.inject.Inject

class ArticlesLocalDataSource @Inject constructor(): ArticlesDataSource {
    override fun fetchArticles(): List<Article> {
        return listOf(Article("test"))
    }
}