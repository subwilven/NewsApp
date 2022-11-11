package com.example.newsapp.di

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.use_cases.FetchArticlesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {


    @Singleton
    @Provides
    fun provideFetchArticlesUseCase(articlesRepository: ArticlesRepository): FetchArticlesUseCase {
        return FetchArticlesUseCase(articlesRepository)
    }

}