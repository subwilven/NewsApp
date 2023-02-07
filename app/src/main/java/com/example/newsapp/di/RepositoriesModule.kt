package com.example.newsapp.di

import com.example.newsapp.data.articles.data_source.local.ArticlesLocalDataSource
import com.example.newsapp.data.articles.data_source.remote.ArticlesRemoteDataSource
import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.data.articles.repository.ArticlesRepositoryImp
import com.example.newsapp.data.providers.datasource.local.ProvidersLocalDataSource
import com.example.newsapp.data.providers.datasource.remote.ProvidersRemoteDataSource
import com.example.newsapp.data.providers.repository.ProvidersRepository
import com.example.newsapp.data.providers.repository.ProvidersRepositoryImp
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

    @Singleton
    @Provides
    fun provideProvidersRepository(
        providersDataSource: ProvidersLocalDataSource,
        remoteDataSource: ProvidersRemoteDataSource
    ): ProvidersRepository {
        return ProvidersRepositoryImp(providersDataSource, remoteDataSource)
    }

}