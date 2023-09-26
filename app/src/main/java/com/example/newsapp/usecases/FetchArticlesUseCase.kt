package com.example.newsapp.usecases

import androidx.paging.PagingData
import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.Article
import com.example.newsapp.usecases.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    dispatcher: CoroutineDispatcher
) :FlowUseCase<PagingData<Article>,FilterData>(dispatcher){

    override fun doWork(params: FilterData): Flow<PagingData<Article>> {
        return articlesRepository.getArticlesStream(params)
    }
}
