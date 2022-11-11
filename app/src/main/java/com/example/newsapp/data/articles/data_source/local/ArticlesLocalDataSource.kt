package com.example.newsapp.data.articles.data_source.local

import com.example.newsapp.data.articles.data_source.ArticlesDataSource
import com.example.newsapp.model.Article

class ArticlesLocalDataSource : ArticlesDataSource {
    override suspend fun fetchArticles(pageNumber :Int): List<Article> {
        return emptyList()
    }
}