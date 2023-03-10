package com.example.newsapp.data.articles.datasource.remote

import com.example.newsapp.model.articles.ArticlesResponse
import com.example.newsapp.model.providers.ProviderResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiServices {

    @GET("top-headlines")
   suspend fun getArticles(
        @Query(value = "pageSize") pageSize: Int,
        @Query(value = "page") pageNumber: Int,
        @Query(value = "country") country: String?,
        @Query(value = "q") query: String?,
        @Query(value = "sources", encoded = true) sources: String?,
        @Query("apiKey") key: String?
    ) : ArticlesResponse

    @GET("top-headlines/sources")
    suspend  fun getProviders(
        @Query("apiKey") key: String?
    ): ProviderResponse
}
