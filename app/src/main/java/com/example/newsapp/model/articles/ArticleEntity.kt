package com.example.newsapp.model.articles


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.newsapp.util.convertToAgoTime
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "article", indices = [Index(value = ["title"], unique = true)])
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "author")
    @SerializedName("author")
    val author: String?,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description: String?,

    @ColumnInfo(name = "article_url")
    @SerializedName("url")
    val articleUrl: String?,

    @ColumnInfo(name = "image_url")
    @SerializedName("urlToImage")
    var imageUrl: String?,

    @ColumnInfo(name = "publishedAt")
    @SerializedName("publishedAt")
    val publishedAt: Date?,

    @ColumnInfo(name = "content")
    @SerializedName("content")
    val content: String?,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false

)

fun ArticleEntity.asUiModel() = Article(
    id,
    author ?: "",
    title,
    description?: "",
    articleUrl?: "",
    imageUrl?: "",
    publishedAt?.convertToAgoTime()?: "",
    content?: "",
    isFavorite
)
