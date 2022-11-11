package com.example.newsapp.data.articles.data_source

import com.example.newsapp.model.articles.Article

interface ArticlesDataSource<T> {
   suspend fun fetchArticles(qurey :String,pageNumber :Int) : T
}