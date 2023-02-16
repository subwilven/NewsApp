package com.example.newsapp.data.articles.data_source.remote

import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.ArticlesResponse

interface ArticlesRemoteDataSource {
   suspend fun fetchArticles(
      filterData: FilterData,
      pageSize: Int,
      pageNumber: Int
   ): ArticlesResponse

}