package com.example.newsapp.ui.screens.articles

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.R
import com.example.newsapp.model.articles.Article
import com.example.newsapp.ui.components.EmptyScreen
import com.example.newsapp.ui.components.FavoriteButton
import com.example.newsapp.ui.components.LoadingAsyncImage
import com.example.newsapp.util.Constants.Destinations.ARTICLES


fun NavGraphBuilder.ArticlesScreen(
    onShowSnackBar: suspend (String) -> Unit,
    onArticleClicked: (Article) -> Unit,
    onFilterIconClicked: () -> Unit
) {

    composable(
        route = ARTICLES,
    ) {
        ArticlesRoute(onArticleClicked, onShowSnackBar, onFilterIconClicked)
    }
}


@Composable
private fun ArticlesRoute(
    onArticleClicked: (Article) -> Unit,
    onShowSnackBar: suspend (String) -> Unit,
    onFilterIconClicked: () -> Unit,
    articlesViewModel: ArticlesViewModel = hiltViewModel(),
) {

    val uiState: ArticleUiState by articlesViewModel.uiState.collectAsStateWithLifecycle()
    val articlesList = uiState.articlesDataFlow?.collectAsLazyPagingItems()

    articlesList?.let {

        val shouldShowRedBadge = uiState.shouldShowRedBadge()
        ArticlesScreen(
            articlesList,
            uiState.getSearchFieldInput(),
            onShowSnackBar,
            shouldShowRedBadge,
            articlesViewModel::searchByQuery,
            articlesViewModel::clearSearch,
            articlesViewModel::changeArticleFavoriteState,
            onArticleClicked,
            onFilterIconClicked,
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ArticlesScreen(
    articles: LazyPagingItems<Article>,
    query: String,
    onShowSnackBar: suspend (String) -> Unit,
    showFilterRedBadge: Boolean,
    onQueryChanged: (String) -> Unit,
    onClearIconClicked: () -> Unit,
    onFavoriteButtonClicked: (Article) -> Unit,
    onArticleClicked: (Article) -> Unit,
    onFilterIconClicked: () -> Unit,
) {
    val shouldShowEmptyList = shouldShowEmptyList(articles)
    val shouldFullLoadingProgressBar = shouldShowFullScreenLoading(articles.loadState)
    val fullScreenError = fullScreenError(articles.loadState, articles.itemCount)
    val onRetryClicked = { articles.refresh() }
    val lazyListState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(
        shouldFullLoadingProgressBar, onRetryClicked
    )
    val errorMessage = getErrorState(articles.loadState)

    LaunchedEffect(key1 = errorMessage?.error) {
        if (fullScreenError != null) return@LaunchedEffect
        errorMessage?.let {
            onShowSnackBar(it.error.message ?: "")
        }
    }


    LaunchedEffect(shouldFullLoadingProgressBar) {
        if (shouldFullLoadingProgressBar)
            lazyListState.scrollToItem(0, 0)
    }


    Column {
        SearchAndFilter(
            query,
            showFilterRedBadge,
            onQueryChanged,
            onClearIconClicked,
            onFilterIconClicked
        )

        if (fullScreenError != null) {
            FullScreenError(fullScreenError.error.message ?: "", onRetryClicked)
        } else if (shouldShowEmptyList)
            EmptyScreen(stringResource(id = R.string.no_results_found))
        else {

            Box(
                modifier = Modifier
                    .pullRefresh(pullRefreshState)
                    .fillMaxSize()
            ) {
                if (!shouldFullLoadingProgressBar) {
                    ArticlesList(articles, lazyListState, onFavoriteButtonClicked, onArticleClicked)
                }
                PullRefreshIndicator(
                    shouldFullLoadingProgressBar,
                    pullRefreshState,
                    Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}

@Composable
fun SearchAndFilter(
    query: String,
    showFilterRedBadge: Boolean,
    onQueryChanged: (String) -> Unit,
    onClearIconClicked: () -> Unit,
    onFilterIconClicked: () -> Unit,
) {
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
}

@Composable
fun FullScreenError(message: String, onRetryClicked: () -> Unit) {
    Box(
        Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(text = message)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onRetryClicked,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
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
                SearchIcon()
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
                    ClearIcon(onClearIconClicked)
                }
            }
        }
    )

}

@Composable
private fun SearchIcon() {
    Icon(
        imageVector = Icons.Default.Search,
        null,
        tint = MaterialTheme.colorScheme.outline,
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}

@Composable
private fun ClearIcon(onClearIconClicked: () -> Unit) {
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

@Composable
private fun ArticlesList(
    articles: LazyPagingItems<Article>,
    lazyListState: LazyListState,
    onFavoriteButtonClicked: (Article) -> Unit,
    onArticleClicked: (Article) -> Unit,
) {
    LazyColumn(state = lazyListState) {

        items(articles.itemCount) { index ->
            articles[index]?.let {
                ArticleItem(it, onFavoriteButtonClicked, onArticleClicked)
            }
        }
        when (articles.loadState.append) {
            is LoadState.NotLoading, is LoadState.Error -> Unit
            LoadState.Loading -> {
                item {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}

@Composable
private fun ArticleItem(
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
            LoadingAsyncImage(
                imageUrl = article.imageUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .fillMaxWidth()
                    .height(180.dp),
            )
            ArticleAuthor(article.author)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
            ) {
                ArticleTitle(Modifier.weight(0.9f), title = article.title)
                FavoriteButton(
                    Modifier.weight(0.1f),
                    article.isFavorite,
                    MaterialTheme.colorScheme.onSurfaceVariant,
                ) {
                    onFavoriteButtonClicked.invoke(article)
                }
            }
            ArticlePublishedDate(article.publishDate)
        }
    }
}

@Composable
private fun ArticlePublishedDate(date: String?) {
    date?.let {
        Text(
            color = MaterialTheme.colorScheme.outline,
            style = MaterialTheme.typography.labelSmall,
            text = it
        )
    }
}

@Composable
private fun ArticleAuthor(author: String?) {
    author?.let {
        Text(
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(vertical = 8.dp),
            text = it
        )
    }
}

@Composable
private fun ArticleTitle(modifier: Modifier = Modifier, title: String) {
    Text(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .padding(vertical = 4.dp),
        text = title
    )
}

private fun shouldShowEmptyList(articlesList: LazyPagingItems<Article>) =
    !shouldShowFullScreenLoading(articlesList.loadState) &&
            articlesList.itemCount == 0 && articlesList.loadState.append.endOfPaginationReached

private fun shouldShowFullScreenLoading(loadState: CombinedLoadStates) =
    loadState.mediator?.refresh is LoadState.Loading
            || loadState.refresh is LoadState.Loading

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

@Preview
@Composable
fun ComposablePreview() {
//    ArticlesContent(flow<PagingData<ArticleUi>> {}.collectAsLazyPagingItems(),
//        "", actionFlow(), {}, {})
}
