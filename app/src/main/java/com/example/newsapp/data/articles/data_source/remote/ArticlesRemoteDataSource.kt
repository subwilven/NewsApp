package com.example.newsapp.data.articles.data_source.remote

import com.example.newsapp.model.articles.ArticlesResponse
import com.example.newsapp.model.sources.SourceResponse

interface ArticlesRemoteDataSource {
   suspend fun fetchArticles(query :String?, pageNumber :Int) : ArticlesResponse
   suspend fun fetchSources() : SourceResponse

}