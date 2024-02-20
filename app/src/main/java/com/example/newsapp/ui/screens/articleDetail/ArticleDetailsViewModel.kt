package com.example.newsapp.ui.screens.articleDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.articles.Article
import com.example.newsapp.usecases.GetArticleByIdUseCase
import com.example.newsapp.usecases.ToggleFavoriteStateUseCase
import com.example.newsapp.util.Constants.Args.ARTICLE_ID
import com.example.newsapp.util.Constants.Defaults.FLOW_SUBSCRIPTION_TIMEOUT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    getArticleByIdUseCase: GetArticleByIdUseCase,
    private val toggleFavoriteStateUseCase: ToggleFavoriteStateUseCase,
) : ViewModel() {

    val articleDetails: StateFlow<Article?> =
        getArticleByIdUseCase(getArticleId())
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(FLOW_SUBSCRIPTION_TIMEOUT),
                initialValue = null,
            )

    private fun getArticleId() = savedStateHandle.get<Int>(ARTICLE_ID) ?: 0

    fun toggleArticleFavoriteState() {
        viewModelScope.launch {
            articleDetails.value?.let { toggleFavoriteStateUseCase(it) }
        }
    }

}
