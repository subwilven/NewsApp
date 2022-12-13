package com.example.newsapp.use_cases

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChangeFavoriteStateUseCase @Inject constructor(
    private val repository: ArticlesRepository
)  {

    suspend fun execute(articleUi: ArticleUi)  {
        withContext(Dispatchers.IO){
            val articleId= articleUi.id
            val isFavorite = articleUi.isFavorite.not()
            repository.changeFavoriteState(articleId,isFavorite)
        }
    }
}