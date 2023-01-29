package com.example.newsapp.data.articles.data_source.remote

import com.example.newsapp.BuildConfig
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.ArticlesResponse
import com.example.newsapp.util.DEFAULT_PAGE_SIZE
import javax.inject.Inject

class ArticlesRemoteDataSourceImp @Inject constructor(
    private val articlesServices: ArticlesServices) :
    ArticlesRemoteDataSource {

    override suspend fun fetchArticles(filterData: FilterData, pageNumber: Int): ArticlesResponse {
        return articlesServices.getArticles(
            DEFAULT_PAGE_SIZE,
            pageNumber,
            filterData.getCountry(),
            filterData.searchInput,
            filterData.convertProvidersToString(),
            BuildConfig.API_KEY
        )
    }

    override suspend fun fetchProviders() =articlesServices.getProviders(BuildConfig.API_KEY)
}