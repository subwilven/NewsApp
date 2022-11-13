package com.example.newsapp.data.articles.data_source.remote

import com.example.newsapp.model.articles.Article

interface ArticlesRemoteDataSource {
   suspend fun fetchArticles(query :String?, pageNumber :Int) : List<Article>
}