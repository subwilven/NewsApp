package com.example.newsapp.di

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.use_cases.AddToFavoriteUseCase
import com.example.newsapp.use_cases.FetchArticlesUseCase
import com.example.newsapp.use_cases.GetArticleByIdUseCase
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

    @Singleton
    @Provides
    fun provideAddToFavoriteUseCase(articlesRepository: ArticlesRepository): AddToFavoriteUseCase {
        return AddToFavoriteUseCase(articlesRepository)
    }


    @Singleton
    @Provides
    fun provideGetArticleByIdUseCase(articlesRepository: ArticlesRepository): GetArticleByIdUseCase {
        return GetArticleByIdUseCase(articlesRepository)
    }


}