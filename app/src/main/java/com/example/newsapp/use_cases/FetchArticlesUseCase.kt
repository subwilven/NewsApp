package com.example.newsapp.use_cases

import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.use_cases.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository,
    dispatcher: CoroutineDispatcher
) :FlowUseCase<PagingData<ArticleUi>,FilterData>(dispatcher){

    override fun doWork(params: FilterData): Flow<PagingData<ArticleUi>> {
        return repository.getArticlesStream(params)
            .map { pagingData -> pagingData.map { ArticleUi(it) } }
    }
}