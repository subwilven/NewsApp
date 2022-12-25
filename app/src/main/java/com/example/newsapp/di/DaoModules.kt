package com.example.newsapp.di

import com.example.newsapp.data.AppDatabase
import com.example.newsapp.data.articles.data_source.local.ArticlesDao
import com.example.newsapp.data.articles.data_source.local.SourcesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaoModules{

    @Singleton
    @Provides
    fun provideArticlesDao(appDatabase: AppDatabase): ArticlesDao {
        return appDatabase.articlesDao()
    }

    @Singleton
    @Provides
    fun provideSourcesDao(appDatabase: AppDatabase): SourcesDao {
        return appDatabase.sourcesDao()
    }
}