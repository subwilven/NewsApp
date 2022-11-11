package com.example.newsapp.data.articles.data_source.remote

import com.example.newsapp.data.articles.data_source.ArticlesDataSource
import com.example.newsapp.model.Article
import javax.inject.Inject

class ArticlesRemoteDataSource  @Inject constructor() : ArticlesDataSource {
    override fun fetchArticles(): List<Article> {
        return emptyList()
    }
}