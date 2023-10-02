package com.example.newsapp.ui.screens.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.providers.repository.ProvidersRepository
import com.example.newsapp.model.FilterData
import com.example.newsapp.model.articles.Article
import com.example.newsapp.usecases.FetchArticlesUseCase
import com.example.newsapp.usecases.ToggleFavoriteStateUseCase
import com.example.newsapp.util.DEBOUNCE_SEARCH_INPUT
import com.example.newsapp.util.FLOW_SUBSCRIPTION_TIMEOUT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class ArticlesViewModel @Inject constructor(
    providersRepository: ProvidersRepository,
    private val fetchArticlesUseCase: FetchArticlesUseCase,
    private val toggleFavoriteStateUseCase: ToggleFavoriteStateUseCase,
) : ViewModel() {

    private val searchInputFlow = MutableStateFlow<String?>(null)

    private val filterDataFlow = combine(
        searchInputFlow,
        providersRepository.getSelectedProvidersIds()
    ) { inputSearch, selectedProviders ->
        FilterData(
            searchInput = inputSearch,
            selectedProvidersIds = selectedProviders
        )
    }
    private val pagingListFlow = filterDataFlow
        .debounce(DEBOUNCE_SEARCH_INPUT)
        .distinctUntilChanged()
        .flatMapLatest { filterData ->
            fetchArticlesUseCase(filterData)
        }

    val uiState: StateFlow<ArticleUiState> = filterDataFlow
        .map(::mapToUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(FLOW_SUBSCRIPTION_TIMEOUT),
            ArticleUiState(articlesDataFlow = pagingListFlow)
        )

    private fun mapToUiState(filterData: FilterData) = ArticleUiState(
        articlesDataFlow = pagingListFlow,
        filterData = filterData
    )

    fun searchByQuery(searchInput: String?) {
        searchInputFlow.tryEmit(searchInput)
    }

    fun changeArticleFavoriteState(article: Article) {
        viewModelScope.launch {
            toggleFavoriteStateUseCase(article)
        }
    }

    fun clearSearch() = searchByQuery(null)
}
