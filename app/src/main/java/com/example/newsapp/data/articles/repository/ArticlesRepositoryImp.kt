package com.example.newsapp.data.articles.repository

import androidx.paging.*
import com.example.newsapp.data.articles.data_source.local.ArticlesLocalDataSource
import com.example.newsapp.data.articles.data_source.remote.ArticlesRemoteDataSource
import com.example.newsapp.data.articles.data_source.remote.ArticlesRemoteMediator
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.ArticleEntity
import com.example.newsapp.model.providers.ProviderEntity
import com.example.newsapp.util.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ArticlesRepositoryImp @Inject constructor(
    private val localDataSource: ArticlesLocalDataSource,
    private val remoteDataSource: ArticlesRemoteDataSource,
) : ArticlesRepository {


    override fun getArticlesStream(filterData: FilterData): Flow<PagingData<ArticleEntity>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = ArticlesRemoteMediator(filterData, remoteDataSource, localDataSource),
        ) {
            localDataSource.fetchArticles(filterData.searchInput)
        }.flow
    }

    override  fun getArticleById(articleId: Int) = localDataSource.getArticleById(articleId)

    override fun getFavoritesArticles() = localDataSource.getFavoritesArticles()

    override suspend fun changeFavoriteState(articleId: Int, isFavorite: Boolean) =
        localDataSource.changeFavoriteState(articleId, isFavorite)

    override  suspend fun getProviders(): List<ProviderEntity> {
        val providersList = localDataSource.getProviders()
        return providersList.ifEmpty {
            val fetchedSources = remoteDataSource.fetchProviders().providers
            localDataSource.insertAllSources(fetchedSources)
            localDataSource.getProviders()
        }
    }

}