package com.example.newsapp.data.articles.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.data.articles.data_source.ArticlesDataSource
import com.example.newsapp.model.articles.Article
import com.example.newsapp.ui.articles.UsersDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticlesRepositoryImp @Inject constructor(
    private val localDataSource : ArticlesDataSource,
    private val remoteDataSource : ArticlesDataSource,) : ArticlesRepository{

     //todo add remote mediaotr
    override fun getArticlesStream(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {UsersDataSource(localDataSource)}
        ).flow
    }
}