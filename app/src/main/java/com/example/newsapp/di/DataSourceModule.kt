package com.example.newsapp.di

import com.example.newsapp.data.articles.data_source.local.ArticlesDao
import com.example.newsapp.data.articles.data_source.local.ArticlesLocalDataSource
import com.example.newsapp.data.articles.data_source.local.ArticlesLocalDataSourceImp
import com.example.newsapp.data.articles.data_source.local.ProvidersDao
import com.example.newsapp.data.articles.data_source.remote.ArticlesRemoteDataSource
import com.example.newsapp.data.articles.data_source.remote.ArticlesRemoteDataSourceImp
import com.example.newsapp.data.articles.data_source.remote.ArticlesServices
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
    fun provideArticlesLocalDataSource(articlesDao: ArticlesDao, providersDao: ProvidersDao)
            : ArticlesLocalDataSource {
        return ArticlesLocalDataSourceImp(articlesDao,providersDao)
    }

    @Singleton
    @Provides
    fun provideArticlesRemoteDataSource(articlesServices: ArticlesServices)
            : ArticlesRemoteDataSource {
        return ArticlesRemoteDataSourceImp(articlesServices)
    }

}