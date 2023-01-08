package com.example.newsapp.ui.screens.articleDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.use_cases.GetArticleByIdUseCase
import com.example.newsapp.use_cases.ToggleFavoriteStateUseCase
import com.example.newsapp.util.ARG_ARTICLE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
    private val toggleFavoriteStateUseCase: ToggleFavoriteStateUseCase,
) : ViewModel() {

    private val _articleDetails = MutableStateFlow<ArticleUi?>(null)
    val articleDetails = _articleDetails.asStateFlow()

    init {
        getArticleDetails()
    }

    private fun getArticleDetails(){
        viewModelScope.launch {
            getArticleId()?.let { articleId ->
                _articleDetails.emit(getArticleById(articleId))
            }
        }
    }

    private fun getArticleId() =  savedStateHandle.get<Int>(ARG_ARTICLE_ID)

    private suspend fun getArticleById(articleId:Int) = getArticleByIdUseCase(articleId)

}