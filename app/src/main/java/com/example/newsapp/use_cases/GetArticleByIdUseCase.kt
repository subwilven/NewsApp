package com.example.newsapp.use_cases

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.articles.Article
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.use_cases.base.FlowUseCase
import com.example.newsapp.use_cases.base.ResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetArticleByIdUseCase @Inject constructor(
    private val repository: ArticlesRepository,
    dispatcher: CoroutineDispatcher
)  : FlowUseCase<ArticleUi,Int>(dispatcher){


    override fun doWork(params: Int): Flow<ArticleUi> {
       return repository.getArticleById(params).map {  ArticleUi(it)}
    }
}