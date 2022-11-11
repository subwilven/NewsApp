package com.example.newsapp.di

import com.example.newsapp.data.articles.data_source.local.ArticlesLocalDataSource
import com.example.newsapp.data.articles.data_source.remote.ArticlesRemoteDataSource
import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.data.articles.repository.ArticlesRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Singleton
    @Provides
    fun provideArticlesRepository(
         articlesDataSource: ArticlesLocalDataSource,
         remoteDataSource: ArticlesRemoteDataSource
    ): ArticlesRepository {
        return ArticlesRepositoryImp(articlesDataSource, remoteDataSource)
    }

}