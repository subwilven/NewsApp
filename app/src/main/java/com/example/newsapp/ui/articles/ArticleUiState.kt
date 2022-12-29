package com.example.newsapp.ui.articles

import androidx.paging.PagingData
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.model.sources.SourceUi
import kotlinx.coroutines.flow.Flow

data class ArticleUiState(
    var searchInput :String? = null,
    val articlesDataFlow: Flow<PagingData<ArticleUi>>? = null,
    var sourcesList : List<SourceUi> = emptyList()
)