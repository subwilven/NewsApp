package com.example.newsapp.data.articles.data_source

import com.example.newsapp.model.Article

interface ArticlesDataSource {
    fun fetchArticles() :List<Article>
}