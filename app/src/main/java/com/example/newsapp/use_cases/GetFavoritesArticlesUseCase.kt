package com.example.newsapp.use_cases

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.use_cases.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoritesArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository,
    dispatcher: CoroutineDispatcher
) :FlowUseCase<List<ArticleUi>,Any?>(dispatcher){

    override fun doWork(params: Any?): Flow<List<ArticleUi>> {
        return repository.getFavoritesArticles().map {
            it.map {
                    article -> ArticleUi(article)
            }
        }
    }
}