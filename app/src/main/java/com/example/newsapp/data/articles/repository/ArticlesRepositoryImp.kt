package com.example.newsapp.data.articles.repository

import androidx.paging.*
import com.example.newsapp.data.articles.data_source.local.ArticlesLocalDataSource
import com.example.newsapp.data.articles.data_source.remote.ArticlesRemoteDataSource
import com.example.newsapp.data.articles.data_source.remote.ArticlesRemoteMediator
import com.example.newsapp.model.articles.Article
import com.example.newsapp.util.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ArticlesRepositoryImp @Inject constructor(
    private val localDataSource : ArticlesLocalDataSource,
    private val remoteDataSource : ArticlesRemoteDataSource,) : ArticlesRepository{


    override fun getArticlesStream(query: String?): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = ArticlesRemoteMediator(query,remoteDataSource,localDataSource),
        ) {
            localDataSource.fetchArticles(query)
        }.flow
    }

    override fun getArticleById(articleId: Int) = localDataSource.getArticleById(articleId)
}