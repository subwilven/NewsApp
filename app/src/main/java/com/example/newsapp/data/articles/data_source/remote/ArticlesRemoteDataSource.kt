package com.example.newsapp.data.articles.data_source.remote

import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.ArticlesResponse
import com.example.newsapp.model.providers.ProviderResponse

interface ArticlesRemoteDataSource {
   suspend fun fetchArticles(filterData : FilterData, pageNumber :Int) : ArticlesResponse

}