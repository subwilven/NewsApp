package com.example.newsapp.di

import com.example.newsapp.data.AppDatabase
import com.example.newsapp.data.articles.datasource.local.ArticlesDao
import com.example.newsapp.data.providers.datasource.local.ProvidersDao
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
    fun provideProvidersDao(appDatabase: AppDatabase): ProvidersDao {
        return appDatabase.providersDao()
    }
}
