package com.example.newsapp.ui.articles

import androidx.paging.PagingData
import com.example.newsapp.model.articles.Article
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.model.sources.Source
import com.example.newsapp.model.sources.SourceUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class ArticleUiState(
    var query :String? = null,
    val articlesDataFlow: Flow<PagingData<ArticleUi>>? = null,
    var sourcesList : List<SourceUi> = emptyList()
)