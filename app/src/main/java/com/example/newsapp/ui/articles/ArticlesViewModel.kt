package com.example.newsapp.ui.articles

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

    private val _uiState = MutableStateFlow(ArticleUiState())
    val uiState = _uiState.asStateFlow()

    val actionsChannel = Channel<ArticlesActions>()

    var oldSelectedSourcesList = mutableListOf<ProviderUi>()

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
        }
    }

    private fun Flow<ArticlesActions>.process() = onEach {
        when (it) {
            is ArticlesActions.SearchArticlesAction -> onSearchArticles(it.searchInput)
            is ArticlesActions.AddToFavoriteAction -> changeArticleFavoriteState(it.article)
            else -> {}
//            ArticlesActions.RefreshArticlesAction -> onRefreshMovies()
        }
    }


    private fun onSearchArticles(searchInput: String?) {
        viewModelScope.launch {
            updateUiState(_uiState.value.copy(searchInput = searchInput))
            searchFlow.emit(searchInput)
        }

    }

    private fun changeArticleFavoriteState(articleUi: ArticleUi) {
        viewModelScope.launch {
            toggleFavoriteStateUseCase(articleUi)
        }
    }

    private fun updateUiState(articleUiState: ArticleUiState){
        _uiState.tryEmit(articleUiState)
    }
}