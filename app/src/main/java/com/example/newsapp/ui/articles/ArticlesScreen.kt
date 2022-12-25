package com.example.newsapp.ui.articles

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.util.NewsAppScreens
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticlesScreen(
    navController: NavController,
    showBottomSheet: (@Composable (ColumnScope.() -> Unit)) -> Unit,
    articlesViewModel: ArticlesViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    val coroutineScope = rememberCoroutineScope()

    val uiState = articlesViewModel.uiState

    val articlesList = uiState.articlesDataFlow?.collectAsLazyPagingItems()

    articlesList?.let {
        //todo should we create remember for this callbacks ?
        ArticlesContent(articlesList, uiState.query ?: "",
            onSearchQueryChanged = {
                articlesViewModel.query(it)
            }, onClearSearchClicked = {
                articlesViewModel.query(null)
            }, onArticleClicked = {
                navController.navigate(NewsAppScreens.ArticleDetailsScreen.route + "/${it.id}")
            }, onFavoriteClicked = {
                articlesViewModel.changeArticleFavoriteState(it)
            }, onFilterIconClicked = {
                coroutineScope.launch {
                    articlesViewModel.fetchArticlesSources()
                    showBottomSheet.invoke { SelectSourcesBottomSheet(articlesViewModel) }


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

@Composable
fun LoadingFullScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
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
fun ArticlesContent(
    articles: LazyPagingItems<ArticleUi>, query: String,
    onSearchQueryChanged: (String) -> Unit,
    onArticleClicked: (ArticleUi) -> Unit,
    onFavoriteClicked: (ArticleUi) -> Unit,
    onFilterIconClicked: () -> Unit,
    onClearSearchClicked: () -> Unit
) {
    val shouldShowEmptyList = shouldShowEmptyList(articles)
    val shouldFullLoadingProgressBar = shouldShowFullScreenLoading(articles.loadState)
    val shape = RoundedCornerShape(25.dp)
    Column {
        Surface {
            Row {
                BasicTextField(
                    value = query,
                    onValueChange = onSearchQueryChanged,
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
                                            onClearSearchClicked.invoke()
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
                    ArticlesList(articles, onArticleClicked, onFavoriteClicked)
                }

            }

    }
}

@Composable
fun ArticlesList(
    articles: LazyPagingItems<ArticleUi>,
    onArticleClicked: (ArticleUi) -> Unit,
    onFavoriteClicked: (ArticleUi) -> Unit
) {
    LazyColumn {
        items(articles.itemCount) { index ->
            articles.get(index)?.let {
                ArticleItem(it, onArticleClicked, onFavoriteClicked)
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
    article: ArticleUi, onArticleClicked: (ArticleUi) -> Unit,
    onFavoriteClicked: (ArticleUi) -> Unit,
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
                        onFavoriteClicked.invoke(article)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectSourcesBottomSheet(articlesViewModel: ArticlesViewModel) {

    val uiState = articlesViewModel.uiState
    val state = rememberScrollState()
    FlowRow(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .verticalScroll(state),
        mainAxisSpacing = 4.dp,
    ) {
        uiState.sourcesList.onEachIndexed { index , sourceUi ->

            FilterChip(
                selected = sourceUi.isSelected,
                onClick = {
                    articlesViewModel.onSelectSource(sourceUi,index)
                },
                border = BorderStroke(
                    ChipDefaults.OutlinedBorderSize,
                    Color.Red
                ),
//                colors = ChipDefaults.chipColors(
//                    backgroundColor = Color.White,
//                    contentColor = Color.Red
//                ),

            ) {
                Text(sourceUi.name)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen() {

}

@Preview
@Composable
@ExperimentalMaterialApi
fun ComposablePreview() {
    ArticlesContent(flow<PagingData<ArticleUi>> {}.collectAsLazyPagingItems(),
        "", {}, {}, {}, {}, {})
}