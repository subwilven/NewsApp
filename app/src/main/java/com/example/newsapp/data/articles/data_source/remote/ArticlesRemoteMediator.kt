package com.example.newsapp.data.articles.data_source.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.newsapp.data.articles.data_source.local.ArticlesLocalDataSource
import com.example.newsapp.model.articles.Article
import com.example.newsapp.util.PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.roundToInt


@OptIn(ExperimentalPagingApi::class)
class ArticlesRemoteMediator(
    private val searchInput: String?,
    private val remoteDatabase: ArticlesRemoteDataSource,
    private val localDatabase: ArticlesLocalDataSource,
) : RemoteMediator<Int, Article>() {

    var pageNumber = 1

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                // pass null to load the first page.
                LoadType.REFRESH -> {
                    pageNumber = 1
                    pageNumber
                }
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    ++pageNumber
                }
            }


            val response = remoteDatabase.fetchArticles(searchInput,loadKey)
            if (loadType == LoadType.REFRESH) {
                localDatabase.insertAllArticlesAndDeleteOld(response.articles)
            }else{
                localDatabase.insertAllArticles(response.articles)
            }

            val hasMoreData = response.totalResults.toDouble().div(PAGE_SIZE).roundToInt() > pageNumber
            MediatorResult.Success(
                endOfPaginationReached = !hasMoreData
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }


}
