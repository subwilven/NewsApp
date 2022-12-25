package com.example.newsapp.ui.articles

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.model.sources.SourceUi
import com.example.newsapp.use_cases.ChangeFavoriteStateUseCase
import com.example.newsapp.use_cases.FetchArticlesUseCase
import com.example.newsapp.use_cases.FetchSourcesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(FlowPreview::class)
@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val fetchArticlesUseCase: FetchArticlesUseCase,
    private val fetchSourcesUseCase: FetchSourcesUseCase,
    private val changeFavoriteStateUseCase: ChangeFavoriteStateUseCase,
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

    var oldSelectedSourcesList = mutableListOf<SourceUi>()

    private val searchFlow = MutableSharedFlow<String?>()

    init {
        val searches = searchFlow
            .debounce(500)
            .distinctUntilChanged()
            .onStart { emit(null) }

        val articlesDataFlow = searches.flatMapLatest { query ->
            fetchArticlesUseCase.execute(query)
        } .cachedIn(viewModelScope)

        _uiState.tryEmit( _uiState.value.copy(articlesDataFlow = articlesDataFlow))

    }

    fun query(query: String?) {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(query = query))
            searchFlow.emit(query)
        }
    }

    fun changeArticleFavoriteState(articleUi: ArticleUi){
        viewModelScope.launch {
            changeFavoriteStateUseCase.execute(articleUi)
        }
    }

    fun fetchArticlesSources(){
        viewModelScope.launch {
          fetchSourcesUseCase. execute().onEach {
              _uiState.emit(_uiState.value.copy(sourcesList = it))
           }.collect()
        }
    }

    fun saveCurrentStateOfSelectedSources(){

    }

    fun onSelectSource(sourceUi: SourceUi, index: Int) {
        val newSourcesList = _uiState.value.sourcesList.toMutableList()
        newSourcesList[index] =sourceUi.copy(isSelected = sourceUi.isSelected.not() )
        _uiState.tryEmit( uiState.value.copy(sourcesList = newSourcesList ))
    }
    fun applySelectedSourcesFilter(){


    }
}