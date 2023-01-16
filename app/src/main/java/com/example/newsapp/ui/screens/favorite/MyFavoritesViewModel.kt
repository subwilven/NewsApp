package com.example.newsapp.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.ui.screens.articles.ArticleUiState
import com.example.newsapp.use_cases.ToggleFavoriteStateUseCase
import com.example.newsapp.use_cases.GetFavoritesArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFavoritesViewModel @Inject constructor(
    private val getFavoritesArticlesUseCase: GetFavoritesArticlesUseCase,
) : ViewModel() {


    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getFavoriteArticles()
    }


    private fun getFavoriteArticles(){
        getFavoritesArticlesUseCase(null)
        getFavoritesArticlesUseCase.observe().onEach {
            updateUiState(_uiState.value.copy(isLoading = false, favoriteArticles = it))
        }.launchIn(viewModelScope)
    }

    private fun updateUiState(favoriteUiState: FavoriteUiState){
        _uiState.tryEmit(favoriteUiState)
    }

}