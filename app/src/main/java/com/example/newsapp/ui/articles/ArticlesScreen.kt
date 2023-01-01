package com.example.newsapp.ui.articles

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.model.providers.ProviderUi
import com.example.newsapp.navigation.AppNavigator
import com.example.newsapp.navigation.Destination
import com.example.newsapp.ui.components.LoadingFullScreen
import com.example.newsapp.ui.providers.ProvidersListContent
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticlesScreen(
    appNavigator: AppNavigator,
    showBottomSheet: (@Composable (ColumnScope.() -> Unit)) -> Unit,
    articlesViewModel: ArticlesViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    val coroutineScope = rememberCoroutineScope()
    val uiState: ArticleUiState by articlesViewModel.uiState.collectAsState()
    val articlesList = uiState.articlesDataFlow?.collectAsLazyPagingItems()


    articlesList?.let {
        //todo should we create remember for this callbacks ?
        ArticlesContent(articlesList, uiState.searchInput ?: "",
            articlesViewModel.actionsChannel,
            onArticleClicked = {
                appNavigator.tryNavigateTo(Destination.ArticleDetailsScreen(it.id.toString()))
                //navController.navigate(NewsAppScreens.ArticleDetailsScreen.route + "/${it.id}")
            }, onFilterIconClicked = {
                coroutineScope.launch {
                    showBottomSheet.invoke {
                        ProvidersListContent()
//                        { sourceUi, index ->
//                            articlesViewModel.onSelectProvider(sourceUi, index)
//                        }
                    }
                }
            })
    }

    //todo use sdie effects to show snackbar see https://developer.android.com/jetpack/compose/side-effects
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

private fun shouldShowEmptyList(articlesList: LazyPagingItems<ArticleUi>) =
    articlesList.loadState.refresh is LoadState.NotLoading &&
            articlesList.itemCount == 0

private fun shouldShowArticlesList(loadState: CombinedLoadStates) =
    loadState.source.refresh is LoadState.NotLoading ||
            loadState.mediator?.refresh is LoadState.NotLoading

private fun shouldShowFullScreenLoading(loadState: CombinedLoadStates) =
    loadState.mediator?.refresh is LoadState.Loading


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
fun ArticlesContent(
    articles: LazyPagingItems<ArticleUi>,
    query: String,
    actionFlow: Channel<ArticlesActions>,
    onArticleClicked: (ArticleUi) -> Unit,
    onFilterIconClicked: () -> Unit
) {
    val shouldShowEmptyList = shouldShowEmptyList(articles)
    val shouldFullLoadingProgressBar = shouldShowFullScreenLoading(articles.loadState)
    val lazyListState = rememberLazyListState()
    val shape = RoundedCornerShape(25.dp)
    Column {
        Surface {
            Row {
                BasicTextField(
                    value = query,
                    onValueChange = {
                        actionFlow.trySend(ArticlesActions.SearchArticlesAction(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.8f)
                        .wrapContentHeight()
                        .clip(shape)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Max)
                                .background(Color.LightGray, shape)
                                .padding(8.dp),
                        ) {
                            Image(
                                painterResource(R.drawable.ic_search_24),
                                "content description",
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                contentAlignment = CenterStart
                            ) {
                                innerTextField()
                                if (query.isEmpty())
                                    Text(
                                        text = "search",
                                        color = Color.Gray,
                                        style = MaterialTheme.typography.caption,
                                    )
                            }

                            if (query.isNotEmpty()) {
                                Image(painterResource(R.drawable.ic_cancel_24),
                                    "content description",
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .clickable {
                                            actionFlow.trySend(
                                                ArticlesActions.SearchArticlesAction(
                                                    null
                                                )
                                            )
                                        })
                            }
                        }
                    }
                )
                Image(painterResource(R.drawable.ic_filter_24),
                    "content description",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clickable {
                            onFilterIconClicked.invoke()
                        })
            }
        }
        if (shouldFullLoadingProgressBar) {
            LoadingFullScreen()
        } else
            SwipeRefresh(
                state = rememberSwipeRefreshState(shouldShowEmptyList),
                onRefresh = { articles.refresh() },
            ) {
                if (shouldShowEmptyList) {

                } else {
                    ArticlesList(articles,lazyListState, actionFlow, onArticleClicked)
                }
            }

    }
}

@Composable
fun ArticlesList(
    articles: LazyPagingItems<ArticleUi>,
    lazyListState: LazyListState,
    actionFlow: Channel<ArticlesActions>,
    onArticleClicked: (ArticleUi) -> Unit,
) {
    LazyColumn(state = lazyListState) {

        items(articles.itemCount) { index ->
            articles.get(index)?.let {
                ArticleItem(it, actionFlow, onArticleClicked)
                Divider(color = Color.LightGray, thickness = 1.dp)
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
fun ArticleItem(
    article: ArticleUi,
    actionFlow: Channel<ArticlesActions>,
    onArticleClicked: (ArticleUi) -> Unit,
) {
    Column(modifier = Modifier
        .padding(16.dp)
        .clickable {
            onArticleClicked.invoke(article)
        }) {
        AsyncImage(
            model = article.imageUrl,
            error = painterResource(R.drawable.no_image_placeholder),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = article.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .height(180.dp)
        )

        article.author?.let {
            Text(
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(vertical = 8.dp),
                text = it
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
        ) {
            Text(
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .weight(0.9f),
                text = article.title
            )
            Image(painter = if (article.isFavorite) painterResource(R.drawable.ic_favorite_24)
            else painterResource(R.drawable.ic_favorite_border_24),
                contentDescription = "sdf",
                modifier = Modifier
                    .padding(4.dp)
                    .weight(0.1f)
                    .clickable {
                        actionFlow.trySend(ArticlesActions.AddToFavoriteAction(article))
                    })
        }

        article.publishedAt?.let {
            Text(
                style = MaterialTheme.typography.overline,
                text = it
            )
        }
    }
}

@Composable
fun MainScreen() {

}

@Preview
@Composable
@ExperimentalMaterialApi
fun ComposablePreview() {
//    ArticlesContent(flow<PagingData<ArticleUi>> {}.collectAsLazyPagingItems(),
//        "", actionFlow(), {}, {})
}