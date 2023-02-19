package com.example.newsapp.use_cases

import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.Article
import com.example.newsapp.model.articles.ArticleEntity
import com.example.newsapp.model.articles.asUiModel
import com.example.newsapp.use_cases.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    dispatcher: CoroutineDispatcher
) :FlowUseCase<PagingData<Article>,FilterData>(dispatcher){

    override fun doWork(params: FilterData): Flow<PagingData<Article>> {
        return articlesRepository.getArticlesStream(params)
            .map { pagingData -> pagingData.map(ArticleEntity::asUiModel) }

    }
}
