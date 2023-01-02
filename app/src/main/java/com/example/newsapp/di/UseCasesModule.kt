package com.example.newsapp.di

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.use_cases.ToggleFavoriteStateUseCase
import com.example.newsapp.use_cases.FetchArticlesUseCase
import com.example.newsapp.use_cases.GetArticleByIdUseCase
import com.example.newsapp.use_cases.GetFavoritesArticlesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {


    @Singleton
    @Provides
    fun provideFetchArticlesUseCase(articlesRepository: ArticlesRepository,dispatcher: CoroutineDispatcher): FetchArticlesUseCase {
        return FetchArticlesUseCase(articlesRepository,dispatcher)
    }

    @Singleton
    @Provides
    fun provideAddToFavoriteUseCase(articlesRepository: ArticlesRepository,dispatcher: CoroutineDispatcher): ToggleFavoriteStateUseCase {
        return ToggleFavoriteStateUseCase(articlesRepository,dispatcher)
    }


    @Singleton
    @Provides
    fun provideGetArticleByIdUseCase(articlesRepository: ArticlesRepository,dispatcher: CoroutineDispatcher)
       : GetArticleByIdUseCase {
        return GetArticleByIdUseCase(articlesRepository,dispatcher)
    }

    @Singleton
    @Provides
    fun provideGetFavoritesArticlesUseCase(articlesRepository: ArticlesRepository,dispatcher: CoroutineDispatcher)
      : GetFavoritesArticlesUseCase {
        return GetFavoritesArticlesUseCase(articlesRepository,dispatcher)
    }


}