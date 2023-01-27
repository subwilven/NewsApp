package com.example.newsapp.ui.screens.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.Article
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.use_cases.ToggleFavoriteStateUseCase
import com.example.newsapp.use_cases.FetchArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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

    private val searchFlow = MutableStateFlow<String?>(null)

    private val selectedProvidersFlow = MutableStateFlow<List<Provider>>(emptyList())

    init {

        val filterDataFlow = combineFilterFlows()
        val articlesPagingDataFlow = createPagingListFlow(filterDataFlow)
        updateUiStateOnFilterChanges(filterDataFlow, articlesPagingDataFlow)
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
        articlesDataFlow: Flow<PagingData<Article>>
    ) {
        filterDataFlow.onEach { filterData ->
            _uiState.value = ArticleUiState(
                articlesDataFlow = articlesDataFlow,
                filterData = filterData
            )
        }.launchIn(viewModelScope)

    }

    fun searchByQuery(searchInput: String?) {
        searchFlow.tryEmit(searchInput)
    }

     fun onProvidersSelected(selectedProviders: List<Provider>) {
        selectedProvidersFlow.tryEmit(selectedProviders)
    }

    fun changeArticleFavoriteState(article: Article) {
        viewModelScope.launch {
            toggleFavoriteStateUseCase(article)
        }
    }

    fun clearSearch() = searchByQuery(null)
}