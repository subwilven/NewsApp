package com.example.newsapp.ui.screens.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.model.FilterData
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
    //todo check hilt extention

    //todo handle showing snackbar on error look at Jetnews

    private val _uiState = MutableStateFlow(ArticleUiState())
    val uiState = _uiState.asStateFlow()

    val actionsChannel = Channel<ArticlesActions>()

    private val searchFlow = MutableStateFlow<String?>(null)

    private val selectedProvidersFlow = MutableStateFlow<List<ProviderUi>>(emptyList())

    init {

        val filterDataFlow = combineFilterFlows()
        val articlesPagingDataFlow = createPagingListFlow(filterDataFlow)
        updateUiStateOnFilterChanges(filterDataFlow, articlesPagingDataFlow)

        actionsChannel.receiveAsFlow().onEach { processActions(it) }.launchIn(viewModelScope)
    }

    private fun combineFilterFlows() =
        combine(searchFlow, selectedProvidersFlow) { inputSearch, selectedProviders ->
            FilterData(
                searchInput = inputSearch,
                selectedProviders = selectedProviders
            )
        }

    private fun createPagingListFlow(filterDataFlow: Flow<FilterData>) = filterDataFlow
        .debounce(400)
        .distinctUntilChanged()
        .flatMapLatest { filterData ->
            fetchArticlesUseCase.produce(filterData)
        }.cachedIn(viewModelScope)


    private fun updateUiStateOnFilterChanges(
        filterDataFlow: Flow<FilterData>,
        articlesDataFlow: Flow<PagingData<ArticleUi>>
    ) {
        filterDataFlow.onEach { filterData ->
            _uiState.value = ArticleUiState(
                articlesDataFlow = articlesDataFlow,
                filterData = filterData
            )
        }.launchIn(viewModelScope)

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
        searchFlow.tryEmit(searchInput)
    }

    private fun onProvidersSelected(selectedProviders: List<ProviderUi>) {
        selectedProvidersFlow.tryEmit(selectedProviders)
    }

    private fun changeArticleFavoriteState(articleUi: ArticleUi) {
        viewModelScope.launch {
            toggleFavoriteStateUseCase(articleUi)
        }
    }

}