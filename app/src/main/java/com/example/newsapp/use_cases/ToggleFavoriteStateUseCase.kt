package com.example.newsapp.use_cases

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.use_cases.base.NoResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ToggleFavoriteStateUseCase @Inject constructor(
    private val repository: ArticlesRepository,
    dispatcher: CoroutineDispatcher
) : NoResultUseCase<ArticleUi>(dispatcher) {

    override suspend fun run(params: ArticleUi) {
        val articleId= params.id
        val isFavorite = params.isFavorite.not()
        repository.changeFavoriteState(articleId,isFavorite)
    }
}