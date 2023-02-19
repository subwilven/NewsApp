package com.example.newsapp.data.articles.data_source.local

import androidx.paging.PagingSource
import com.example.newsapp.model.articles.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ArticlesLocalDataSource {
   fun fetchArticles(searchInput :String?) : PagingSource<Int,ArticleEntity>
   suspend fun insertAllArticles(articles : List<ArticleEntity>)
   suspend fun insertAllArticlesAndDeleteOld(articles : List<ArticleEntity>)
   suspend fun deleteAllArticles()
   fun getArticleById(articleId :Int): Flow<ArticleEntity>
   fun getFavoritesArticles() : Flow<List<ArticleEntity>>
   suspend fun changeFavoriteState(articleId:Int,isFavorite :Boolean)

}
