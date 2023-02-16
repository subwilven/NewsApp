package com.example.newsapp.data.articles.data_source.remote

import com.example.newsapp.BuildConfig
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.ArticlesResponse
import javax.inject.Inject

class ArticlesRemoteDataSourceImp @Inject constructor(
    private val newsApiServices: NewsApiServices) :
    ArticlesRemoteDataSource {

    override suspend fun fetchArticles(
        filterData: FilterData,
        pageSize: Int,
        pageNumber: Int
    ): ArticlesResponse {
        return newsApiServices.getArticles(
            pageSize,
            pageNumber,
            filterData.getCountry(),
            filterData.searchInput,
            filterData.convertProvidersToString(),
            BuildConfig.API_KEY
        )
    }


}