package com.example.newsapp.ui.screens.articles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.model.providers.ProviderUi
import com.example.newsapp.use_cases.ToggleFavoriteStateUseCase
import com.example.newsapp.use_cases.FetchArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val fetchArticlesUseCase: FetchArticlesUseCase,
    private val toggleFavoriteStateUseCase: ToggleFavoriteStateUseCase,
) : ViewModel() {

    //todo 1)handle erorr in paging
    // handle ui states 4) add search feature 5)
    //todo 5) add general network state handling
    //todo pares dates via retfofit
    //todo gradle catalog
    //todo add dark theme
    //todo check hilt extention
    //todo  LaunchedEffect to apply the sort

    //todo handle showing snackbar on error look at Jetnews

    private val _uiState = MutableStateFlow(ArticleUiState())
    val uiState = _uiState.asStateFlow()

    val actionsChannel = Channel<ArticlesActions>()

    private val searchFlow = MutableSharedFlow<String?>()

    init {
        val searches = searchFlow
            .debounce(500)
            .distinctUntilChanged()
            .onStart { emit(null) }

        val articlesDataFlow = searches.flatMapLatest { searchInput ->
            fetchArticlesUseCase.produce(searchInput)
        }.cachedIn(viewModelScope)

        updateUiState(_uiState.value.copy(articlesDataFlow = articlesDataFlow))
        actionsChannel.receiveAsFlow().onEach { processActions(it) }.launchIn(viewModelScope)
    }

    private fun processActions(articlesActions: ArticlesActions) {
        when (articlesActions) {
            is ArticlesActions.SearchArticlesAction
            -> onSearchArticles(articlesActions.searchInput)
            is ArticlesActions.AddToFavoriteAction
            -> changeArticleFavoriteState(articlesActions.article)
            is ArticlesActions.FilterByProvidersAction
            -> onProvidersSelected(articlesActions.selectedProviders)
        }
    }

    private fun onSearchArticles(searchInput: String?) {
        viewModelScope.launch {
            updateUiState(
                _uiState.value.copy(
                    filterData = _uiState.value.filterData.copy(
                        searchInput = searchInput
                    )
                )
            )
            searchFlow.emit(searchInput)
        }
    }

    private fun  onProvidersSelected(selectedProviders : List<ProviderUi>){
        Log.e("selectedProviders","selectedProviders ${selectedProviders.size}")
    }

    private fun changeArticleFavoriteState(articleUi: ArticleUi) {
        viewModelScope.launch {
            toggleFavoriteStateUseCase(articleUi)
        }
    }

    private fun updateUiState(articleUiState: ArticleUiState) {
        _uiState.tryEmit(articleUiState)
    }
}