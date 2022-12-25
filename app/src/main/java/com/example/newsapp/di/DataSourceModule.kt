package com.example.newsapp.di

import com.example.newsapp.data.articles.data_source.local.ArticlesDao
import com.example.newsapp.data.articles.data_source.local.ArticlesLocalDataSource
import com.example.newsapp.data.articles.data_source.local.ArticlesLocalDataSourceImp
import com.example.newsapp.data.articles.data_source.local.SourcesDao
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
    fun provideArticlesLocalDataSource(articlesDao: ArticlesDao,sourcesDao: SourcesDao)
            : ArticlesLocalDataSource {
        return ArticlesLocalDataSourceImp(articlesDao,sourcesDao)
    }

    @Singleton
    @Provides
    fun provideArticlesRemoteDataSource(articlesServices: ArticlesServices)
            : ArticlesRemoteDataSource {
        return ArticlesRemoteDataSourceImp(articlesServices)
    }

}