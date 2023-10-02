package com.example.newsapp.usecases

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.articles.Article
import com.example.newsapp.usecases.base.NoResultUseCase
import javax.inject.Inject

class ToggleFavoriteStateUseCase @Inject constructor(
    private val repository: ArticlesRepository,
) : NoResultUseCase<Article>() {

    override suspend fun run(params: Article) {
        val articleId = params.id
        val isFavorite = params.isFavorite.not()
        repository.changeFavoriteState(articleId, isFavorite)
    }
}
