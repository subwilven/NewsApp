package com.example.newsapp.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Result
import com.example.newsapp.asResult
import com.example.newsapp.model.articles.Article
import com.example.newsapp.usecases.GetFavoritesArticlesUseCase
import com.example.newsapp.util.FLOW_SUBSCRIPTION_TIMEOUT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyFavoritesViewModel @Inject constructor(
    getFavoritesArticlesUseCase: GetFavoritesArticlesUseCase,
) : ViewModel() {


    val uiState: StateFlow<FavoriteUiState> = getFavoritesArticlesUseCase(Unit)
        .asResult()
        .map(::mapToUiState)
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(FLOW_SUBSCRIPTION_TIMEOUT),
            initialValue = FavoriteUiState(isLoading = true)
        )

    private fun mapToUiState(response: Result<List<Article>>) = when (response) {
        is Result.Success -> FavoriteUiState(favoriteArticles = response.data)
        is Result.Error -> FavoriteUiState(errorMessage = response.exception?.message)
        is Result.Loading -> FavoriteUiState(isLoading = true)
    }


}
