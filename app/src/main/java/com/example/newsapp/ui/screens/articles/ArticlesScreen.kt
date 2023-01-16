package com.example.newsapp.ui.screens.articles

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
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
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.model.providers.ProviderUi
import com.example.newsapp.navigation.AppNavigator
import com.example.newsapp.navigation.navigateToArticleDetails
import com.example.newsapp.ui.components.FavoriteButton
import com.example.newsapp.ui.components.LoadingFullScreen
import com.example.newsapp.ui.components.MyDialog
import com.example.newsapp.ui.screens.providers.ProvidersScreen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ArticlesScreen(
    appNavigator: AppNavigator,
    articlesViewModel: ArticlesViewModel = hiltViewModel(),
) {

    val coroutineScope = rememberCoroutineScope()
    val uiState: ArticleUiState by articlesViewModel.uiState.collectAsStateWithLifecycle()
    val articlesList = uiState.articlesDataFlow?.collectAsLazyPagingItems()

    // Dialog state Manager
    val dialogState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    // Code to Show and Dismiss Dialog
    if (dialogState.value) {
        DialogProvidersSelection(dialogState){ selectedProviders->
            articlesViewModel.actionsChannel.trySend(
                ArticlesActions.FilterByProvidersAction(
                    selectedProviders
                )
            )
        }
    }


    articlesList?.let {
        val shouldShowRedBadage = uiState.filterData.selectedProviders.isNotEmpty()
        //todo should we create remember for this callbacks ?
        ArticlesContent(articlesList, uiState.filterData.searchInput ?: "",
            shouldShowRedBadage,
            articlesViewModel.actionsChannel,
            onArticleClicked = { article ->
                navigateToArticleDetails(appNavigator, article)
            }, onFilterIconClicked = {
                dialogState.value = true
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

@Composable
private fun DialogProvidersSelection(
    dialogState: MutableState<Boolean>,
    onProvidersSelected: (List<ProviderUi>) -> Unit
) {
    MyDialog(
        R.string.select_providers,
        R.string.ok,
        dialogState,
    ) {
        ProvidersScreen()
        { selectedProviders ->
            onProvidersSelected.invoke(selectedProviders)
        }
    }
}

private fun shouldShowEmptyList(articlesList: LazyPagingItems<ArticleUi>) =
    articlesList.loadState.refresh is LoadState.NotLoading &&
            articlesList.itemCount == 0

private fun shouldShowArticlesList(loadState: CombinedLoadStates) =
    loadState.source.refresh is LoadState.NotLoading ||
            loadState.mediator?.refresh is LoadState.NotLoading

private fun shouldShowFullScreenLoading(loadState: CombinedLoadStates) =
    loadState.mediator?.refresh is LoadState.Loading


//fun showSnackBar(
//    scaffoldState: ScaffoldState,
//    coroutineScope: CoroutineScope, errorMessage: String
//) {
//    coroutineScope.launch {
//        scaffoldState.snackbarHostState.showSnackbar(
//            message = errorMessage,
//            duration = SnackbarDuration.Long
//        )
//    }
//}

@Composable
fun ArticlesContent(
    articles: LazyPagingItems<ArticleUi>,
    query: String,
    showFilterRedBadge: Boolean,
    actionFlow: Channel<ArticlesActions>,
    onArticleClicked: (ArticleUi) -> Unit,
    onFilterIconClicked: () -> Unit
) {
    val shouldShowEmptyList = shouldShowEmptyList(articles)
    val shouldFullLoadingProgressBar = shouldShowFullScreenLoading(articles.loadState)
    val lazyListState = rememberLazyListState()

    Column {
        Surface {
            Row {
                SearchInputField(Modifier.weight(0.85f), query, actionFlow)
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .padding(end = 8.dp)
                        .weight(0.1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(painterResource(R.drawable.ic_filter_24),
                        null,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                            .clickable {
                                onFilterIconClicked.invoke()
                            })
                    if (showFilterRedBadge)
                        BadgeRedCircle(Modifier.align(Alignment.TopEnd))
                }
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
                    //todo
                } else {
                    ArticlesList(articles, lazyListState, actionFlow, onArticleClicked)
                }
            }

    }
}

@Composable
fun BadgeRedCircle(modifier: Modifier) {
    Canvas(modifier = modifier.size(10.dp), onDraw = {
        val size = 11.dp.toPx()
        drawCircle(
            color = Color.Red,
            radius = size / 2f
        )
    })
}

@Composable
fun SearchInputField(modifier: Modifier, query: String, actionFlow: Channel<ArticlesActions>) {
    val shape = RoundedCornerShape(25.dp)
    BasicTextField(
        value = query,
        onValueChange = {
            actionFlow.trySend(ArticlesActions.SearchArticlesAction(it))
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape)
            .padding(vertical = 8.dp)
            .padding(start = 16.dp),
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
                            style = MaterialTheme.typography.labelMedium,
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

}

@Composable
fun ArticlesList(
    articles: LazyPagingItems<ArticleUi>,
    lazyListState: LazyListState,
    actionFlow: Channel<ArticlesActions>,
    onArticleClicked: (ArticleUi) -> Unit,
) {
    LazyColumn(state = lazyListState, modifier = Modifier.padding(top = 8.dp)) {

        items(articles.itemCount) { index ->
            articles.get(index)?.let {
                ArticleItem(it, actionFlow, onArticleClicked)
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
) {Card(modifier = Modifier
    .padding(horizontal = 8.dp, vertical = 4.dp).clickable {
        onArticleClicked.invoke(article)
    },
    colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant)
){
    Column(modifier = Modifier
        .padding(12.dp)
        ) {
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
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium,
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
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .weight(0.9f),
                text = article.title
            )
            FavoriteButton(Modifier .weight(0.1f),
                article.isFavorite,
                MaterialTheme.colorScheme.onSurfaceVariant){
                actionFlow.trySend(ArticlesActions.AddToFavoriteAction(article))
            }
        }

        article.publishedAt?.let {
            Text(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelSmall,
                text = it
            )
        }
    }}
}

@Composable
fun MainScreen() {

}

@Preview
@Composable

fun ComposablePreview() {
//    ArticlesContent(flow<PagingData<ArticleUi>> {}.collectAsLazyPagingItems(),
//        "", actionFlow(), {}, {})
}