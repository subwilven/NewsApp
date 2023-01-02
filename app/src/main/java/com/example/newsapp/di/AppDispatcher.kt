package com.example.newsapp.di

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.use_cases.FetchArticlesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDispatcher {

    @Singleton
    @Provides
    fun workerDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}