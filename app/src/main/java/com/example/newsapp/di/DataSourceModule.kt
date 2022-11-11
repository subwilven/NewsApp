package com.example.newsapp.di

import com.example.newsapp.data.articles.data_source.ArticlesDataSource
import com.example.newsapp.data.articles.data_source.local.ArticlesLocalDataSource
import com.example.newsapp.data.articles.data_source.remote.ArticlesRemoteDataSource
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
    @LocalDataSource
    fun provideArticlesLocalDataSource(): ArticlesDataSource {
        return ArticlesLocalDataSource()
    }

    @Singleton
    @Provides
    @RemoteDataSource
    fun provideArticlesRemoteDataSource(articlesServices: ArticlesServices): ArticlesDataSource {
        return ArticlesRemoteDataSource(articlesServices)
    }

}