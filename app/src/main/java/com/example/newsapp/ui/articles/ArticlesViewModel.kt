package com.example.newsapp.ui.articles

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.use_cases.ChangeFavoriteStateUseCase
import com.example.newsapp.use_cases.FetchArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(FlowPreview::class)
@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val fetchArticlesUseCase: FetchArticlesUseCase,
    private val changeFavoriteStateUseCase: ChangeFavoriteStateUseCase,
) : ViewModel() {

    //todo 1)handle erorr in paging
    // handle ui states 4) add search feature 5)
    //todo 5) add general network state handling
    //todo pares dates via retfofit
    //todo gradle catalog
    //todo add dark theme
    //todo check hilt extention

    //todo use savedState handler

    //todo  LaunchedEffect to apply the sort
    var uiState by mutableStateOf(ArticleUiState())
        private set

    private val searchFlow = MutableSharedFlow<String?>()

    init {
        val searches = searchFlow
            .debounce(500)
            .distinctUntilChanged()
            .onStart { emit(null) }

        val articlesDataFlow = searches.flatMapLatest { query ->
            fetchArticlesUseCase.execute(query)
        } .cachedIn(viewModelScope)

        uiState = uiState.copy(articlesDataFlow = articlesDataFlow)

    }

    fun query(query: String?) {
        viewModelScope.launch {
            uiState = uiState.copy(query = query)
            searchFlow.emit(query)
        }
    }

    fun changeArticleFavoriteState(articleUi: ArticleUi){
        viewModelScope.launch {
            changeFavoriteStateUseCase.execute(articleUi)
        }

    }

}