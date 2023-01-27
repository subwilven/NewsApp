package com.example.newsapp.use_cases

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.articles.Article
import com.example.newsapp.model.articles.ArticleEntity
import com.example.newsapp.model.articles.asUiModel
import com.example.newsapp.use_cases.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetArticleByIdUseCase @Inject constructor(
    private val repository: ArticlesRepository,
    dispatcher: CoroutineDispatcher
)  : FlowUseCase<Article,Int>(dispatcher){


    override fun doWork(params: Int): Flow<Article> {
       return repository.getArticleById(params).map(ArticleEntity::asUiModel)
    }
}