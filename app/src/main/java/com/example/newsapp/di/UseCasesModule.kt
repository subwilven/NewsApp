package com.example.newsapp.di

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.use_cases.ChangeFavoriteStateUseCase
import com.example.newsapp.use_cases.FetchArticlesUseCase
import com.example.newsapp.use_cases.GetArticleByIdUseCase
import com.example.newsapp.use_cases.GetFavoritesArticlesUseCase
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
    fun provideAddToFavoriteUseCase(articlesRepository: ArticlesRepository): ChangeFavoriteStateUseCase {
        return ChangeFavoriteStateUseCase(articlesRepository)
    }


    @Singleton
    @Provides
    fun provideGetArticleByIdUseCase(articlesRepository: ArticlesRepository)
       : GetArticleByIdUseCase {
        return GetArticleByIdUseCase(articlesRepository)
    }

    @Singleton
    @Provides
    fun provideGetFavoritesArticlesUseCase(articlesRepository: ArticlesRepository)
      : GetFavoritesArticlesUseCase {
        return GetFavoritesArticlesUseCase(articlesRepository)
    }


}