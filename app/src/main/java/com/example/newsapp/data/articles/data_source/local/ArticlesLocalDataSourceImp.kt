package com.example.newsapp.data.articles.data_source.local

import androidx.paging.PagingSource
import com.example.newsapp.model.articles.Article
import com.example.newsapp.model.sources.Source
import kotlinx.coroutines.flow.Flow

class ArticlesLocalDataSourceImp(private val articlesDao :ArticlesDao,private val sourcesDao: SourcesDao) :
    ArticlesLocalDataSource {

    override fun fetchArticles(query: String?): PagingSource<Int,Article> {
        return articlesDao.getArticlesByQuery(query)
    }

    override suspend fun insertAllArticles(articles: List<Article>) {
        articlesDao.insertAll(articles)
    }

    override suspend fun insertAllArticlesAndDeleteOld(articles: List<Article>) {
        articlesDao.insertAndDeleteOldArticles(articles)
    }

    override suspend fun deleteAllArticles() {
        articlesDao.deleteAllArticles()
    }

    override suspend fun getArticleById(articleId :Int) = articlesDao.getArticleById(articleId)

    override fun getFavoritesArticles() =articlesDao.getFavoritesArticles()

    override suspend fun changeFavoriteState(articleId: Int, isFavorite: Boolean){
        articlesDao.updateFavoriteState(articleId,isFavorite)
    }

    override fun getSources() =sourcesDao.getAllSources()
    override suspend fun insertAllSources(sources: List<Source>) {
        sourcesDao.insertAll(sources)
    }
}