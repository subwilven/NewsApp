package com.example.newsapp.data.articles.data_source.remote

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.articles.data_source.ArticlesDataSource
import com.example.newsapp.model.Article
import com.example.newsapp.util.PAGE_SIZE
import javax.inject.Inject

class ArticlesRemoteDataSource @Inject constructor(
    private val articlesServices: ArticlesServices) :
    ArticlesDataSource {

    override suspend fun fetchArticles(pageNumber: Int): List<Article> {
        return articlesServices.getArticles(
            "top-headlines",
            PAGE_SIZE,
            pageNumber,
            "us",
            BuildConfig.API_KEY
        ).articles
    }

}