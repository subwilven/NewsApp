package com.example.newsapp.data.articles.data_source.local

import androidx.paging.PagingSource
import androidx.room.*
import com.example.newsapp.model.articles.Article

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Article>)

    @Query(
        "SELECT * FROM article WHERE " +
                "(:queryString IS NULL OR title LIKE :queryString) " +
                "OR (:queryString IS NULL OR description LIKE :queryString )" +
                "ORDER BY publishedAt DESC, title ASC"
    )
    fun getArticlesByQuery(queryString: String?): PagingSource<Int, Article>

    @Query("DELETE FROM article WHERE title LIKE (:queryString OR description LIKE :queryString )")
    suspend fun clearArticlesByQuery(queryString: String?)
}