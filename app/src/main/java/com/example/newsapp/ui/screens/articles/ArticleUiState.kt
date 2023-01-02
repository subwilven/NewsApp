package com.example.newsapp.ui.screens.articles

import androidx.paging.PagingData
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.model.providers.ProviderUi
import kotlinx.coroutines.flow.Flow

data class ArticleUiState(
    var searchInput :String? = null,
    val articlesDataFlow: Flow<PagingData<ArticleUi>>? = null,
)