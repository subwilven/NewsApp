package com.example.newsapp.data.articles.data_source.remote

import com.example.newsapp.model.articles.Article
import com.example.newsapp.model.articles.ArticlesResponse

interface ArticlesRemoteDataSource {
   suspend fun fetchArticles(query :String?, pageNumber :Int) : ArticlesResponse
}