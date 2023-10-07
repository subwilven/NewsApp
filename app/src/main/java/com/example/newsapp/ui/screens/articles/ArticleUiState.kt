package com.example.newsapp.ui.screens.articles

import androidx.paging.PagingData
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.Article
import kotlinx.coroutines.flow.Flow

data class ArticleUiState(
    val filterData: FilterData = FilterData(null, hashSetOf()),
    val articlesDataFlow: Flow<PagingData<Article>>? = null,
) {
    fun shouldShowRedBadge() = filterData.selectedProvidersIds.isNotEmpty()

}
