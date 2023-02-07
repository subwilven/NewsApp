package com.example.newsapp.data.articles.data_source.remote

import com.example.newsapp.BuildConfig
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.ArticlesResponse
import com.example.newsapp.util.DEFAULT_PAGE_SIZE
import javax.inject.Inject

class ArticlesRemoteDataSourceImp @Inject constructor(
    private val newsApiServices: NewsApiServices) :
    ArticlesRemoteDataSource {

    override suspend fun fetchArticles(filterData: FilterData, pageNumber: Int): ArticlesResponse {
        return newsApiServices.getArticles(
            DEFAULT_PAGE_SIZE,
            pageNumber,
            filterData.getCountry(),
            filterData.searchInput,
            filterData.convertProvidersToString(),
            BuildConfig.API_KEY
        )
    }


}