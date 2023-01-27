package com.example.newsapp.data.articles.data_source.local

import androidx.paging.PagingSource
import com.example.newsapp.model.articles.ArticleEntity
import com.example.newsapp.model.providers.ProviderEntity

class ArticlesLocalDataSourceImp(private val articlesDao :ArticlesDao,private val providersDao: ProvidersDao) :
    ArticlesLocalDataSource {

    override fun fetchArticles(searchInput: String?): PagingSource<Int,ArticleEntity> {
        return articlesDao.getArticlesByQuery(searchInput)
    }

    override suspend fun insertAllArticles(articles: List<ArticleEntity>) {
        articlesDao.insertAll(articles)
    }

    override suspend fun insertAllArticlesAndDeleteOld(articles: List<ArticleEntity>) {
        articlesDao.insertAndDeleteOldArticles(articles)
    }

    override suspend fun deleteAllArticles() {
        articlesDao.deleteAllArticles()
    }

    override fun getArticleById(articleId :Int) = articlesDao.getArticleById(articleId)

    override fun getFavoritesArticles() =articlesDao.getFavoritesArticles()

    override suspend fun changeFavoriteState(articleId: Int, isFavorite: Boolean){
        articlesDao.updateFavoriteState(articleId,isFavorite)
    }

    override suspend fun getProviders() =providersDao.getAllProvider()
    override suspend fun getProviderCounts() =providersDao.getProviderCounts()

    override suspend fun insertAllSources(sources: List<ProviderEntity>) {
        providersDao.insertAll(sources)
    }
}