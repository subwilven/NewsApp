package com.example.newsapp.ui.articles

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.data.articles.repository.ArticlesRepositoryImp
import com.example.newsapp.model.Article
import com.example.newsapp.use_cases.FetchArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val fetchArticlesUseCase: FetchArticlesUseCase
) : ViewModel() {

    //todo 1) apply dagger 2)use retrofit 2) create remote meduator  3) handle ui states 4) add search feature
    //todo inject

    //todo use savedState handler
    val initialQuery: String = " "

    //todo  LaunchedEffect to apply the sort
    var uiState by mutableStateOf(ArticleUiState())
        private set

    val articlesDataFlow: Flow<PagingData<Article>>

//    StateFlow<Result<List<Feed>>> = flow {
//        emit(fetchFeedUseCase(Unit))
//    }.onEach {
//
//    }.stateIn(viewModelScope,
//        SharingStarted.WhileSubscribed(5000),
//        Result.Loading)

    init {
        articlesDataFlow = fetchArticlesUseCase(Unit)
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