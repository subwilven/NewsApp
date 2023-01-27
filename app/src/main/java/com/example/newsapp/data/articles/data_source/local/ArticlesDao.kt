package com.example.newsapp.data.articles.data_source.local

import androidx.paging.PagingSource
import androidx.room.*
import com.example.newsapp.model.articles.ArticleEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(articles: List<ArticleEntity>)

    @Query(
        "SELECT * FROM article WHERE " +
                "(:queryString IS NULL OR title LIKE '%' || :queryString || '%') " +
                "OR (:queryString IS NULL OR description LIKE '%' || :queryString || '%' )" +
                "ORDER BY publishedAt DESC, title ASC"
    )
    fun getArticlesByQuery(queryString: String?): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM article WHERE isFavorite = 0")
    suspend fun deleteAllArticles()

    @Query("SELECT * FROM article WHERE id IS :articleId")
    fun getArticleById(articleId :Int) : Flow<ArticleEntity>

    @Query("SELECT * FROM article WHERE isFavorite = 1")
    fun getFavoritesArticles() : Flow<List<ArticleEntity>>

    @Query("UPDATE article SET  isFavorite = :isFavorite WHERE id =:articleId ")
    fun updateFavoriteState(articleId:Int,isFavorite:Boolean) : Int

    @Transaction
    suspend fun insertAndDeleteOldArticles(articles: List<ArticleEntity>) {
        deleteAllArticles()
        insertAll(articles)
    }
}