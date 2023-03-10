package com.example.newsapp.data.articles.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.data.articles.datasource.local.ArticlesLocalDataSource
import com.example.newsapp.data.articles.datasource.remote.ArticlesRemoteDataSource
import com.example.newsapp.data.articles.datasource.remote.ArticlesRemoteMediator
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.ArticleEntity
import com.example.newsapp.util.DEFAULT_INITIAL_PAGE_MULTIPLIER
import com.example.newsapp.util.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ArticlesRepositoryImp @Inject constructor(
    private val localDataSource: ArticlesLocalDataSource,
    private val remoteDataSource: ArticlesRemoteDataSource,
) : ArticlesRepository {


    override fun getArticlesStream(filterData: FilterData): Flow<PagingData<ArticleEntity>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = DEFAULT_PAGE_SIZE * DEFAULT_INITIAL_PAGE_MULTIPLIER,
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = ArticlesRemoteMediator(filterData, remoteDataSource, localDataSource),
        ) {
            localDataSource.fetchArticles(filterData.searchInput)
        }.flow
    }

    override  fun getArticleById(articleId: Int) = localDataSource.getArticleById(articleId)

    override fun getFavoritesArticles() = localDataSource.getFavoritesArticles()

    override suspend fun changeFavoriteState(articleId: Int, isFavorite: Boolean) =
        localDataSource.changeFavoriteState(articleId, isFavorite)

}
