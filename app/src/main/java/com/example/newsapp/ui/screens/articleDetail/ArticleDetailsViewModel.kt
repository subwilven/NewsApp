package com.example.newsapp.ui.screens.articleDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.use_cases.ToggleFavoriteStateUseCase
import com.example.newsapp.use_cases.GetArticleByIdUseCase
import com.example.newsapp.util.ARG_ARTICLE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
    private val toggleFavoriteStateUseCase: ToggleFavoriteStateUseCase,
) : ViewModel() {

    var articleDetails by mutableStateOf<ArticleUi?>(null)
        private set

    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>(ARG_ARTICLE_ID)?.let { articleId ->
                val article = getArticleById(articleId)
                articleDetails = ArticleUi(article)
            }
        }
    }

    private suspend fun getArticleById(articleId:Int) = getArticleByIdUseCase(articleId)

}