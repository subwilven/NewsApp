package com.example.newsapp.ui.screens.articles

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import com.example.newsapp.model.articles.Article
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.navigation.navigateToArticleDetails
import com.example.newsapp.ui.components.FavoriteButton
import com.example.newsapp.ui.components.LoadingFullScreen
import com.example.newsapp.ui.components.MyDialog
import com.example.newsapp.ui.main.LocalAppNavigator
import com.example.newsapp.ui.main.LocalSnackbarDelegate
import com.example.newsapp.ui.screens.providers.ProvidersScreen
import com.example.newsapp.ui.screens.providers.ProvidersViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.*
import java.util.*


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ArticlesScreen(
    articlesViewModel: ArticlesViewModel = hiltViewModel(),
) {

    val uiState: ArticleUiState by articlesViewModel.uiState.collectAsStateWithLifecycle()
    val articlesList = uiState.articlesDataFlow?.collectAsLazyPagingItems()

    // Dialog state Manager
    val dialogState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    // Code to Show and Dismiss Dialog
    if (dialogState.value) {
        DialogProvidersSelection(
            dialogState = dialogState,
            onProvidersSelected = articlesViewModel::onProvidersSelected
        )
    }

    articlesList?.let {
        val shouldShowRedBadage = uiState.filterData.selectedProviders.isNotEmpty()
        val appNavigator = LocalAppNavigator.current
        //todo should we create remember for this callbacks ?
        ArticlesContent(
            articlesList,
            uiState.filterData.searchInput ?: "",
            shouldShowRedBadage,
            articlesViewModel::searchByQuery,
            articlesViewModel::clearSearch,
            articlesViewModel::changeArticleFavoriteState,
            onArticleClicked = { article ->
                navigateToArticleDetails(appNavigator, article)
            },
            onFilterIconClicked = {
                dialogState.value = true
            },
            onRetryClicked = {articlesList.refresh()}
        )
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
    providersViewModel: ProvidersViewModel = hiltViewModel(),
    dialogState: MutableState<Boolean>,
    onProvidersSelected: (List<Provider>) -> Unit
) {
    MyDialog(
        R.string.select_providers,
        R.string.apply_filter,
        R.string.clear_filter,
        dialogState,
        onPositiveClicked = providersViewModel::applyFilter,
        onNegativeClicked = providersViewModel::clearFilter
    ) {
        ProvidersScreen(providersViewModel) {
            dialogState.value = false
            onProvidersSelected.invoke(providersViewModel.getSelectedProviders())
        }
    }
}

private fun shouldShowEmptyList(articlesList: LazyPagingItems<Article>) =
    articlesList.loadState.refresh is LoadState.NotLoading &&
            articlesList.itemCount == 0

private fun shouldShowArticlesList(loadState: CombinedLoadStates) =
    loadState.source.refresh is LoadState.NotLoading ||
            loadState.mediator?.refresh is LoadState.NotLoading

private fun shouldShowFullScreenLoading(loadState: CombinedLoadStates) =
    loadState.mediator?.refresh is LoadState.Loading

private fun fullScreenError(
    loadState: CombinedLoadStates,
    itemsCount: Int
): LoadState.Error? {
    return if (loadState.mediator?.refresh is LoadState.Error && itemsCount == 0)
        loadState.mediator?.refresh as LoadState.Error
    else
        null
}

private fun getErrorState(loadState: CombinedLoadStates): LoadState.Error? {
    return loadState.source.append as? LoadState.Error
        ?: loadState.append as? LoadState.Error
        ?: loadState.refresh as? LoadState.Error
}
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
    articles: LazyPagingItems<Article>,
    query: String,
    showFilterRedBadge: Boolean,
    onQueryChanged: (String) -> Unit,
    onClearIconClicked: () -> Unit,
    onFavoriteButtonClicked: (Article) -> Unit,
    onArticleClicked: (Article) -> Unit,
    onFilterIconClicked: () -> Unit,
    onRetryClicked: () -> Unit,
    ) {
    val shouldShowEmptyList = shouldShowEmptyList(articles)
    val shouldFullLoadingProgressBar = shouldShowFullScreenLoading(articles.loadState)
    val lazyListState = rememberLazyListState()
    val fullScreenError = fullScreenError(articles.loadState, articles.itemCount)
    if (fullScreenError == null) {
        val erorrMessage = getErrorState(articles.loadState)
        erorrMessage?.let {
            LocalSnackbarDelegate.current?.showSnackbar(erorrMessage.error.message ?: "")
        }
    }



    Column {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.inverseOnSurface)
                .height(IntrinsicSize.Min)
        ) {
            SearchInputField(Modifier.weight(0.85f), query, onQueryChanged, onClearIconClicked)
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .padding(end = 8.dp)
                    .weight(0.1f)
                    .align(Alignment.CenterVertically)
            ) {
                IconButton(modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp),
                    onClick = { onFilterIconClicked.invoke() }) {
                    Icon(
                        painterResource(R.drawable.ic_filter_24),
                        null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                if (showFilterRedBadge)
                    BadgeRedCircle(Modifier.align(Alignment.TopEnd))
            }
        }

        if (shouldFullLoadingProgressBar) {
            LoadingFullScreen()
        } else if (fullScreenError!= null) {
            FullScreenError(fullScreenError.error.message?:"",onRetryClicked)
        } else
            SwipeRefresh(
                state = rememberSwipeRefreshState(false),
                onRefresh = { articles.refresh() },
            ) {
                if (shouldShowEmptyList) {
                    EmptyScreen()
                } else {
                    ArticlesList(articles, lazyListState, onFavoriteButtonClicked, onArticleClicked)
                }
            }

    }
}
@Composable
fun EmptyScreen(){
    Box(
        Modifier.fillMaxSize()
    ) {
        Text(modifier = Modifier.align(Alignment.Center) ,
            text = stringResource(id = R.string.no_data_avaiable))
    }
}

@Composable
fun FullScreenError(message: String, onRetryClicked: () -> Unit) {
    Box(
        Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(text = message)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetryClicked,modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = stringResource(id = R.string.retry))
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
fun SearchInputField(
    modifier: Modifier,
    query: String,
    onQueryChanged: (String) -> Unit,
    onClearIconClicked: () -> Unit
) {
    val shape = MaterialTheme.shapes.large
    BasicTextField(
        value = query,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
        onValueChange = onQueryChanged,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape)
            .padding(vertical = 8.dp)
            .padding(start = 16.dp),
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .background(MaterialTheme.colorScheme.surface, shape)
                    .padding(8.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    null,
                    tint = MaterialTheme.colorScheme.outline,
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
                            color = MaterialTheme.colorScheme.outline,
                            style = MaterialTheme.typography.labelMedium,
                        )
                }

                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = onClearIconClicked,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }
    )

}

@Composable
fun ArticlesList(
    articles: LazyPagingItems<Article>,
    lazyListState: LazyListState,
    onFavoriteButtonClicked: (Article) -> Unit,
    onArticleClicked: (Article) -> Unit,
) {
    LazyColumn(state = lazyListState) {

        items(articles.itemCount) { index ->
            articles.get(index)?.let {
                ArticleItem(it, onFavoriteButtonClicked, onArticleClicked)
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
    article: Article,
    onFavoriteButtonClicked: (Article) -> Unit,
    onArticleClicked: (Article) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable {
                onArticleClicked.invoke(article)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            AsyncImage(
                model = article.imageUrl,
                error = painterResource(R.drawable.no_image_placeholder),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = article.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .fillMaxWidth()
                    .height(180.dp)
            )

            article.author?.let {
                Text(
                    color = MaterialTheme.colorScheme.onSurface,
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
                FavoriteButton(
                    Modifier.weight(0.1f),
                    article.isFavorite,
                    MaterialTheme.colorScheme.onSurfaceVariant,

                    ) {
                    onFavoriteButtonClicked.invoke(article)
                }
            }

            article.publishedAt?.let {
                Text(
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.labelSmall,
                    text = it
                )
            }
        }
    }
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