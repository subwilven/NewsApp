package com.example.newsapp.data.providers.datasource.remote

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.articles.data_source.remote.NewsApiServices

class ProvidersRemoteDataSourceImp(private val newsApiServices: NewsApiServices) :
    ProvidersRemoteDataSource {

    override suspend fun fetchProviders() = newsApiServices.getProviders(BuildConfig.API_KEY)

}