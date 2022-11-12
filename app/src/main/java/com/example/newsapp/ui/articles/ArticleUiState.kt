package com.example.newsapp.ui.articles

import androidx.paging.PagingData
import com.example.newsapp.model.articles.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class ArticleUiState(
    var loading: Boolean = true,
    var errorMessage :String? = null,
    var articlesList: Flow<PagingData<Article>> = flowOf()
)