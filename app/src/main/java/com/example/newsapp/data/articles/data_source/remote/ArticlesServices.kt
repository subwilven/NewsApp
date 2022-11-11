package com.example.newsapp.data.articles.data_source.remote

import com.example.newsapp.model.articles.ArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticlesServices {

    @GET("{path}")
   suspend fun getArticles(
        @Path(value = "path") path: String?,
        @Query(value = "pageSize") pageSize: Int,
        @Query(value = "page") pageNumber: Int,
        @Query(value = "country") country: String,
    //    @Query(value = "sources", encoded = true) sources: String?,
        @Query("apiKey") key: String?
    ) : ArticlesResponse

}