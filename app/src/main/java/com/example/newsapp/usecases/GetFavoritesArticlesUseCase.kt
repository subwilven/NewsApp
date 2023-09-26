package com.example.newsapp.usecases

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.articles.Article
import com.example.newsapp.usecases.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository,
    dispatcher: CoroutineDispatcher
) :FlowUseCase<List<Article>,Any?>(dispatcher){

    override fun doWork(params: Any?): Flow<List<Article>> {
        return repository.getFavoritesArticles()
    }
}
