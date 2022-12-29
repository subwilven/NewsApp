package com.example.newsapp.data.articles.repository

import androidx.paging.*
import com.example.newsapp.data.articles.data_source.local.ArticlesLocalDataSource
import com.example.newsapp.data.articles.data_source.remote.ArticlesRemoteDataSource
import com.example.newsapp.data.articles.data_source.remote.ArticlesRemoteMediator
import com.example.newsapp.model.articles.Article
import com.example.newsapp.model.sources.Source
import com.example.newsapp.util.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ArticlesRepositoryImp @Inject constructor(
    private val localDataSource: ArticlesLocalDataSource,
    private val remoteDataSource: ArticlesRemoteDataSource,
) : ArticlesRepository {


    override fun getArticlesStream(searchInput: String?): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = ArticlesRemoteMediator(searchInput, remoteDataSource, localDataSource),
        ) {
            localDataSource.fetchArticles(searchInput)
        }.flow
    }

    override suspend fun getArticleById(articleId: Int) = localDataSource.getArticleById(articleId)

    override fun getFavoritesArticles() = localDataSource.getFavoritesArticles()

    override suspend fun changeFavoriteState(articleId: Int, isFavorite: Boolean) =
        localDataSource.changeFavoriteState(articleId, isFavorite)

    override suspend fun getSources(): Flow<List<Source>> {

        val flow = localDataSource.getSources()

        val fetchedSources = remoteDataSource.fetchSources().sources
        localDataSource.insertAllSources(fetchedSources)

        return flow
    }

}