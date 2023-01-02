package com.example.newsapp.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.use_cases.ToggleFavoriteStateUseCase
import com.example.newsapp.use_cases.GetFavoritesArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFavoritesViewModel @Inject constructor(
    getFavoritesArticlesUseCase: GetFavoritesArticlesUseCase,
    private val toggleFavoriteStateUseCase: ToggleFavoriteStateUseCase,
) : ViewModel() {


//    val favoritesArticles: Flow<List<ArticleUi>> = getFavoritesArticlesUseCase()


    init {
//        fetchProvidersUseCase(null)
//        fetchProvidersUseCase.observe()



    }

    fun changeArticleFavoriteState(articleUi: ArticleUi){
        viewModelScope.launch {
            toggleFavoriteStateUseCase(articleUi)
        }

    }

}