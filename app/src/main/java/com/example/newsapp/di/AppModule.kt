package com.example.newsapp.di

import android.content.Context
import com.example.newsapp.data.AppDatabase
import com.example.newsapp.data.articles.data_source.ArticlesDataSource
import com.example.newsapp.data.articles.data_source.local.ArticlesLocalDataSource
import com.example.newsapp.data.articles.data_source.remote.ArticlesRemoteDataSource
import com.example.newsapp.data.articles.data_source.remote.ArticlesServices
import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.data.articles.repository.ArticlesRepositoryImp
import com.example.newsapp.use_cases.FetchArticlesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.buildDatabase(context)
    }

    @Singleton
    @Provides
    fun provideFetchArticlesUseCase(articlesRepository: ArticlesRepository): FetchArticlesUseCase {
        return FetchArticlesUseCase(articlesRepository)
    }

    @Singleton
    @Provides
    fun provideArticlesRepository(
        @RemoteDataSource articlesDataSource: ArticlesDataSource,
        @RemoteDataSource remoteDataSource: ArticlesDataSource
    ): ArticlesRepository {
        return ArticlesRepositoryImp(articlesDataSource, remoteDataSource)
    }

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