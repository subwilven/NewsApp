package com.example.newsapp.ui.articles

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.model.articles.ArticleUi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


@Composable
fun ArticlesScreen(
    articlesViewModel: ArticlesViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    val uiState = articlesViewModel.uiState
    val articlesList = uiState.articlesDataFlow?.collectAsLazyPagingItems()

    articlesList?.let {

        ArticlesContent(articlesList, uiState.query ?: "",onValueChange = {
            articlesViewModel.query(it)
        }, onClearClicked = {
            articlesViewModel.query(null)
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
    onValueChange: (String) -> Unit,
    onClearClicked : () -> Unit
) {
    val shouldShowEmptyList = shouldShowEmptyList(articles)
    val shouldFullLoadingProgressBar = shouldShowFullScreenLoading(articles.loadState)
    val shape = RoundedCornerShape(25.dp)
    Column {
        Surface {

            BasicTextField(
                value = query,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(shape)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                singleLine = true,
                decorationBox ={ innerTextField ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .background(Color.LightGray, shape)
                        .padding(8.dp),){
                          Image(painterResource(R.drawable.ic_search_24),
                              "content description",
                              modifier = Modifier.padding(horizontal = 4.dp))
                        Box( modifier = Modifier.weight(1f).fillMaxHeight() ,
                            contentAlignment  =CenterStart) {
                            innerTextField()
                            if(query.isEmpty())
                                Text(text = "search",
                                     color = Color.Gray,
                                    style = MaterialTheme.typography.caption,)
                        }

                        if(query.isNotEmpty()) {
                        Image(painterResource(R.drawable.ic_cancel_24),
                            "content description",
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .clickable {
                                    onClearClicked.invoke()
                                })}
                    }
                }
            )
        }
        if(shouldFullLoadingProgressBar){
            LoadingFullScreen()
        }else
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
}

@Composable
fun ArticlesList(articles: LazyPagingItems<ArticleUi>) {
    LazyColumn {
        items(articles.itemCount) { index ->
            articles.get(index)?.let {
                ArticleItem(it)
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
fun ArticleItem(article: ArticleUi) {
    Column(modifier = Modifier.padding(16.dp)) {
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
@Preview
@Composable
@ExperimentalMaterialApi
fun ComposablePreview() {
    ArticlesContent(flow<PagingData<ArticleUi>>{}.collectAsLazyPagingItems(),
        "",{},{})
}