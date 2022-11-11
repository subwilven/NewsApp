package com.example.newsapp.data.articles.data_source.local

import androidx.paging.PagingSource
import com.example.newsapp.data.articles.data_source.ArticlesDataSource
import com.example.newsapp.model.articles.Article

class ArticlesLocalDataSource(private val articlesDao :ArticlesDao) : ArticlesDataSource<PagingSource<Int,Article>> {
    override suspend fun fetchArticles(qurey :String,pageNumber :Int): PagingSource<Int,Article> {
        return articlesDao.getArticlesByQuery(qurey)
    }
}