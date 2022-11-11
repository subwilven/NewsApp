package com.example.newsapp.di

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.newsapp.data.articles.data_source.ArticlesDataSource
import com.example.newsapp.data.articles.data_source.local.ArticlesDao
import com.example.newsapp.data.articles.data_source.local.ArticlesLocalDataSource
import com.example.newsapp.data.articles.data_source.remote.ArticlesRemoteDataSource
import com.example.newsapp.data.articles.data_source.remote.ArticlesServices
import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.data.articles.repository.ArticlesRepositoryImp
import com.example.newsapp.model.articles.Article
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
        @LocalDataSource articlesDataSource: ArticlesDataSource<*>,
        @RemoteDataSource remoteDataSource: ArticlesDataSource<*>
    ): ArticlesRepository {
        return ArticlesRepositoryImp(articlesDataSource
                as ArticlesDataSource<PagingData<Article>>,
            remoteDataSource as ArticlesDataSource<List<Article>>)
    }

    @Singleton
    @Provides
    @LocalDataSource
    fun provideArticlesLocalDataSource(articlesDao: ArticlesDao)
            : ArticlesDataSource<*> {
        return ArticlesLocalDataSource(articlesDao)
    }

    @Singleton
    @Provides
    @RemoteDataSource
    fun provideArticlesRemoteDataSource(articlesServices: ArticlesServices)
            : ArticlesDataSource<*> {
        return ArticlesRemoteDataSource(articlesServices)
    }
}