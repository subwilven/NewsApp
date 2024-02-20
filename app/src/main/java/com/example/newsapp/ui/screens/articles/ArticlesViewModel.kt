package com.example.newsapp.ui.screens.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.data.providers.repository.ProvidersRepository
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.Article
import com.example.newsapp.usecases.FetchArticlesUseCase
import com.example.newsapp.usecases.ToggleFavoriteStateUseCase
import com.example.newsapp.util.Constants.Defaults.DEBOUNCE_SEARCH_INPUT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class ArticlesViewModel @Inject constructor(
    providersRepository: ProvidersRepository,
    private val fetchArticlesUseCase: FetchArticlesUseCase,
    private val toggleFavoriteStateUseCase: ToggleFavoriteStateUseCase,
) : ViewModel() {


    private val _uiState = MutableStateFlow(ArticleUiState())
    val uiState = _uiState.asStateFlow()

    private val searchUserInputFlow = MutableStateFlow<String?>(null)

    private val filterDataFlow = combine(
        searchUserInputFlow,
        providersRepository.getSelectedProvidersIds()
    ) { inputSearch, selectedProviders ->
        FilterData(
            searchInput = inputSearch,
            selectedProvidersIds = selectedProviders
        )
    }.onEach { filterData ->
        _uiState.update {
            it.copy(filterData = filterData)
        }
    }

    private val articlesPagingDataFlow = filterDataFlow
        .debounce(DEBOUNCE_SEARCH_INPUT)
        .distinctUntilChanged()
        .flatMapLatest { filterData ->
            fetchArticlesUseCase(filterData)
        }.cachedIn(viewModelScope)

    init {
        _uiState.update {
            it.copy(articlesDataFlow = articlesPagingDataFlow)
        }
    }

    fun searchByQuery(searchInput: String?) {
        searchUserInputFlow.tryEmit(searchInput)
    }

    fun changeArticleFavoriteState(article: Article) {
        viewModelScope.launch {
            toggleFavoriteStateUseCase(article)
        }
    }

    fun clearSearch() = searchByQuery(null)
}
