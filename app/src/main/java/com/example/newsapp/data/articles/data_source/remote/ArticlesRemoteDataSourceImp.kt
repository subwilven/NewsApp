package com.example.newsapp.data.articles.data_source.remote

import com.example.newsapp.BuildConfig
import com.example.newsapp.model.articles.Article
import com.example.newsapp.util.PAGE_SIZE
import javax.inject.Inject

class ArticlesRemoteDataSourceImp @Inject constructor(
    private val articlesServices: ArticlesServices) :
    ArticlesRemoteDataSource {

    override suspend fun fetchArticles(query: String?, pageNumber: Int): List<Article> {
        return articlesServices.getArticles(
            "top-headlines",
            PAGE_SIZE,
            pageNumber,
            "us",
            BuildConfig.API_KEY
        ).articles
    }
}