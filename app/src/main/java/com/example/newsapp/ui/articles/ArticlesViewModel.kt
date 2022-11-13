package com.example.newsapp.ui.articles

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.example.newsapp.model.articles.Article
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.use_cases.FetchArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject


@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val fetchArticlesUseCase: FetchArticlesUseCase
) : ViewModel() {

    //todo 1)handle erorr in paging
    // handle ui states 4) add search feature 5)
    //todo 5) add general network state handling
    //todo pares dates via retfofit
    //todo gradle catalog
    //todo add dark theme
    //todo check hilt extention

    //todo use savedState handler
    val initialQuery: String = " "

    //todo  LaunchedEffect to apply the sort
    var uiState by mutableStateOf(ArticleUiState())
        private set

    val articlesDataFlow: Flow<PagingData<ArticleUi>>

//    StateFlow<Result<List<Feed>>> = flow {
//        emit(fetchFeedUseCase(Unit))
//    }.onEach {
//
//    }.stateIn(viewModelScope,
//        SharingStarted.WhileSubscribed(5000),
//        Result.Loading)

    init {
        articlesDataFlow = fetchArticlesUseCase(Unit)
            .map {  pagingData-> pagingData.map { ArticleUi(it) } }
            .cachedIn(viewModelScope)
        val articlesFlow = MutableSharedFlow<Article>()
//        val searches = articlesFlow
//            .distinctUntilChanged()
//            .onStart { emit(UiAction.Search(query = initialQuery)) }




//        viewModelScope.launch {
//
//            val pagingFlow =Pager(PagingConfig(pageSize = 20)) {
//                usersDataSource
//            }.flow.cachedIn(viewModelScope)
//
//
//        }
    }
}