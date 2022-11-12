package com.example.newsapp.data.articles.data_source.local

import androidx.paging.PagingSource
import com.example.newsapp.model.articles.Article

class ArticlesLocalDataSourceImp(private val articlesDao :ArticlesDao) :
    ArticlesLocalDataSource {

    override fun fetchArticles(query: String?): PagingSource<Int,Article> {
        return articlesDao.getArticlesByQuery(query)
    }

    override suspend fun insertAllArticles(articles: List<Article>) {
        articlesDao.insertAll(articles)
    }

    override suspend fun deleteAllArticles() {
        articlesDao.deleteAllArticles()
    }
}