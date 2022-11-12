package com.example.newsapp.ui.articles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.R
import com.example.newsapp.model.articles.Article
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ArticlesScreen(
    scaffoldState: ScaffoldState,
    articlesViewModel: ArticlesViewModel = hiltViewModel()
) {

    val uiState = articlesViewModel.uiState
    val coroutineScope = rememberCoroutineScope()
    // val articlesList = articlesViewModel.fetchFeedResult.observeAsState(Result.Loading)
    val articlesList = articlesViewModel.articlesDataFlow.collectAsLazyPagingItems()


    val shouldArticlesList = shouldShowArticlesList(articlesList.loadState)
    val shouldFullLoadingProgressBar = shouldShowFullScreenLoading(articlesList.loadState)

    if (shouldFullLoadingProgressBar) {
        LoadingItem()
    } else {
        ArticlesContent(articlesList)
    }


//    else if(uiState.errorMessage?.isNotEmpty()== true){
//        showSnackBar(scaffoldState,coroutineScope,uiState.errorMessage?:"")
//    }
//    else
//
//        }


}

private fun shouldShowEmptyList(articlesList: LazyPagingItems<Article>) =
    articlesList.loadState.refresh is LoadState.NotLoading &&
            articlesList.itemCount == 0

private fun shouldShowArticlesList(loadState: CombinedLoadStates) =
    loadState.source.refresh is LoadState.NotLoading ||
            loadState.mediator?.refresh is LoadState.NotLoading

private fun shouldShowFullScreenLoading(loadState: CombinedLoadStates) =
    loadState.mediator?.refresh is LoadState.Loading

@Composable
fun LoadingItem() {
    Box(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.teal_700))
    ) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

fun showSnackBar(
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope, errorMessage: String
) {
    coroutineScope.launch {
        scaffoldState.snackbarHostState.showSnackbar(
            message = errorMessage,
            duration = SnackbarDuration.Long
        )
    }
}

@Composable
fun ArticlesContent(articles: LazyPagingItems<Article>) {
    val shouldShowEmptyList = shouldShowEmptyList(articles)

    SwipeRefresh(
        state = rememberSwipeRefreshState(shouldShowEmptyList),
        onRefresh = { articles.refresh() },
    ) {

        if (shouldShowEmptyList) {

        } else {
            ArticlesList(articles)
        }

    }


}

@Composable
fun ArticlesList(articles: LazyPagingItems<Article>) {
    LazyColumn {
        items(articles.itemCount) { index ->
            articles.get(index)?.let {
                Text(text = it.title ?: "")
            }
        }
        when (articles.loadState.append) {
            is LoadState.NotLoading -> Unit
            LoadState.Loading -> {
                item { CircularProgressIndicator() }
            }
            is LoadState.Error -> {

            }
        }
    }
}