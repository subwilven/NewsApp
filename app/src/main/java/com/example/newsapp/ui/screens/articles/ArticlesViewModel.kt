package com.example.newsapp.ui.screens.articles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.data.providers.repository.ProvidersRepository
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.Article
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
    private val providersRepository: ProvidersRepository,
    private val fetchArticlesUseCase: FetchArticlesUseCase,
    private val toggleFavoriteStateUseCase: ToggleFavoriteStateUseCase,
) : ViewModel() {

    //todo pares dates via retfofit
    //todo gradle catalog
    //todo check hilt extention

    private val _uiState = MutableStateFlow(ArticleUiState())
    val uiState = _uiState.asStateFlow()

    private val searchFlow = MutableStateFlow<String?>(null)

    init {

        val filterDataFlow = combineFilterFlows()
        val articlesPagingDataFlow = createPagingListFlow(filterDataFlow)
        updateUiStateOnFilterChanges(filterDataFlow, articlesPagingDataFlow)
    }

    private fun combineFilterFlows() =
        combine(
            searchFlow,
            providersRepository.getSelectedProvidersIds()
        ) { inputSearch, selectedProviders ->
            FilterData(
                searchInput = inputSearch,
                selectedProvidersIds = selectedProviders
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

    fun changeArticleFavoriteState(article: Article) {
        viewModelScope.launch {
            toggleFavoriteStateUseCase(article)
        }
    }

    fun clearSearch() = searchByQuery(null)
}