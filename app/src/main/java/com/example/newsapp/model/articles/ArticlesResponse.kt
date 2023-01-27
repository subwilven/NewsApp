package com.example.newsapp.model.articles

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(@SerializedName("totalResults") val totalResultCount :Int,
                            @SerializedName("articles") val articles: List<ArticleEntity> )