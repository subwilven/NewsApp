package com.example.newsapp.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Feed
import com.example.newsapp.use_cases.FetchFeedUseCase
import javax.inject.Inject
import com.example.newsapp.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val fetchFeedUseCase: FetchFeedUseCase
) : ViewModel() {
//todo use paging3 iwth compose
    val fetchFeedResult: StateFlow<Result<List<Feed>>> = flow {
        emit(fetchFeedUseCase(Unit))
    }.onEach {

    }.stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Result.Loading)
}