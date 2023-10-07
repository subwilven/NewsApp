package com.example.newsapp.data.articles.datasource.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.newsapp.data.articles.datasource.local.ArticlesLocalDataSource
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.ArticleEntity
import com.example.newsapp.model.articles.asEntityModel
import com.example.newsapp.util.DEFAULT_PAGE_SIZE
import com.example.newsapp.util.DEFAULT_START_PAGE_NUMBER
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.roundToInt


@OptIn(ExperimentalPagingApi::class)
class ArticlesRemoteMediator(
    private val filterData: FilterData,
    private val remoteDatabase: ArticlesRemoteDataSource,
    private val localDatabase: ArticlesLocalDataSource,
) : RemoteMediator<Int, ArticleEntity>() {

    private var pageNumber = DEFAULT_START_PAGE_NUMBER

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        return try {
            when (loadType) {
                LoadType.REFRESH -> {
                    pageNumber = DEFAULT_START_PAGE_NUMBER
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }

                else -> {}
            }


            val pageSize = getPageSize(state)

            val response = remoteDatabase.fetchArticles(filterData, pageSize, pageNumber)
            if (pageSize == state.config.initialLoadSize) pageNumber += 2 else pageNumber++

            val fetchedArticlesList = response.articles.map { it.asEntityModel() }
            if (loadType == LoadType.REFRESH) {
                localDatabase.insertAllArticlesAndDeleteOld(fetchedArticlesList)
            } else {
                localDatabase.insertAllArticles(fetchedArticlesList)
            }

            MediatorResult.Success(
                endOfPaginationReached = isDataEndHasBeenReached(response.totalResultCount)
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }


    private fun getPageSize(state: PagingState<Int, ArticleEntity>) =
        if (pageNumber == DEFAULT_START_PAGE_NUMBER)
            state.config.initialLoadSize
        else state.config.pageSize


    private fun isDataEndHasBeenReached(totalResultCount: Int): Boolean {
        return (totalResultCount.toDouble().div(DEFAULT_PAGE_SIZE)
            .roundToInt() > pageNumber).not()
    }
}
