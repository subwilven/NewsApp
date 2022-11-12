package com.example.newsapp.ui.articles

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.model.articles.Article
import com.example.newsapp.ui.main.LocalScaffoldState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ArticlesScreen(
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
//    showSnackBar(LocalScaffoldState.current,
//        coroutineScope,
//        "uytufssft")

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
                ArticleItem(it)
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

@Composable
fun ArticleItem(article: Article) {
    Column(modifier = Modifier.padding(16.dp)) {
        AsyncImage(
            model = article.imageUrl,
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = article.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        )

        article.author?.let {
            Text(
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(vertical = 8.dp),
                text = it
            )
        }
        Text(
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(vertical = 4.dp),
            text = article.title
        )
        article.publishedAt?.let {
            Text(
                style = MaterialTheme.typography.overline,
                text = it
            )
        }
    }
}
