package com.example.newsapp.data.articles.data_source.local

import android.provider.SyncStateContract.Helpers.insert
import androidx.paging.PagingSource
import androidx.room.*
import com.example.newsapp.model.articles.Article
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<Article>)

    @Query(
        "SELECT * FROM article WHERE " +
                "(:queryString IS NULL OR title LIKE '%' || :queryString || '%') " +
                "OR (:queryString IS NULL OR description LIKE '%' || :queryString || '%' )" +
                "ORDER BY publishedAt DESC, title ASC"
    )
    fun getArticlesByQuery(queryString: String?): PagingSource<Int, Article>

    @Query("DELETE FROM article")
    suspend fun deleteAllArticles()

    //todo can we remove the flow here ?
    @Query("SELECT * FROM article WHERE id IS :articleId")
    fun getArticleById(articleId :Int) : Flow<Article>

    @Transaction
    suspend fun insertAndDeleteOldArticles(articles: List<Article>) {
        deleteAllArticles()
        insertAll(articles)
    }
}