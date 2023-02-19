package com.example.newsapp.use_cases

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.articles.Article
import com.example.newsapp.use_cases.base.NoResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ToggleFavoriteStateUseCase @Inject constructor(
    private val repository: ArticlesRepository,
    dispatcher: CoroutineDispatcher
) : NoResultUseCase<Article>(dispatcher) {

    override suspend fun run(params: Article) {
        val articleId= params.id
        val isFavorite = params.isFavorite.not()
        repository.changeFavoriteState(articleId,isFavorite)
    }
}
