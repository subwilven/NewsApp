package com.example.newsapp.usecases

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.articles.Article
import com.example.newsapp.usecases.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticleByIdUseCase @Inject constructor(
    private val repository: ArticlesRepository,
) : FlowUseCase<Article, Int>() {

    override fun doWork(params: Int): Flow<Article> {
        return repository.getArticleById(params)
    }

}
