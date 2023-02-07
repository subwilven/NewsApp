package com.example.newsapp.di

import com.example.newsapp.data.articles.data_source.local.ArticlesDao
import com.example.newsapp.data.articles.data_source.local.ArticlesLocalDataSource
import com.example.newsapp.data.articles.data_source.local.ArticlesLocalDataSourceImp
import com.example.newsapp.data.providers.datasource.local.ProvidersDao
import com.example.newsapp.data.articles.data_source.remote.ArticlesRemoteDataSource
import com.example.newsapp.data.articles.data_source.remote.ArticlesRemoteDataSourceImp
import com.example.newsapp.data.articles.data_source.remote.NewsApiServices
import com.example.newsapp.data.providers.datasource.local.ProvidersLocalDataSource
import com.example.newsapp.data.providers.datasource.local.ProvidersLocalDataSourceImp
import com.example.newsapp.data.providers.datasource.remote.ProvidersRemoteDataSource
import com.example.newsapp.data.providers.datasource.remote.ProvidersRemoteDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun provideArticlesLocalDataSource(articlesDao: ArticlesDao)
            : ArticlesLocalDataSource {
        return ArticlesLocalDataSourceImp(articlesDao)
    }

    @Singleton
    @Provides
    fun provideProvidersLocalDataSource(providersDao: ProvidersDao)
            : ProvidersLocalDataSource {
        return ProvidersLocalDataSourceImp(providersDao)
    }

    @Singleton
    @Provides
    fun provideArticlesRemoteDataSource(newsApiServices: NewsApiServices)
            : ArticlesRemoteDataSource {
        return ArticlesRemoteDataSourceImp(newsApiServices)
    }

    @Singleton
    @Provides
    fun provideProvidersRemoteDataSource(newsApiServices: NewsApiServices)
            : ProvidersRemoteDataSource {
        return ProvidersRemoteDataSourceImp(newsApiServices)
    }
}