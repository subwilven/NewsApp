package com.example.newsapp.data.articles.data_source.local

import androidx.paging.PagingSource
import com.example.newsapp.model.articles.ArticleEntity

class ArticlesLocalDataSourceImp(private val articlesDao :ArticlesDao) :
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

}
