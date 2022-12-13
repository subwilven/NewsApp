package com.example.newsapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.use_cases.ChangeFavoriteStateUseCase
import com.example.newsapp.use_cases.GetFavoritesArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFavoritesViewModel @Inject constructor(
    getFavoritesArticlesUseCase: GetFavoritesArticlesUseCase,
    private val changeFavoriteStateUseCase: ChangeFavoriteStateUseCase,
) : ViewModel() {


    val favoritesArticles: Flow<List<ArticleUi>> = getFavoritesArticlesUseCase.execute()


    init {




    }

    fun changeArticleFavoriteState(articleUi: ArticleUi){
        viewModelScope.launch {
            changeFavoriteStateUseCase.execute(articleUi)
        }

    }

}