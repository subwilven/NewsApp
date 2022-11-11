package com.example.newsapp.ui.articles

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.articles.data_source.ArticlesDataSource
import com.example.newsapp.model.articles.Article

class UsersDataSource(private val query : String,
                      private val articlesDataSource : ArticlesDataSource<List<Article>>) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val nextPageNumber = params.key ?: 1
            //todo handle result
            //todo handle get this source the data source
            val response : List<Article> = articlesDataSource.fetchArticles(query,nextPageNumber)
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isNotEmpty()) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}