package com.example.newsapp.ui.screens.articleDetail

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.navigation.AppNavigator
import com.example.newsapp.navigation.launchWebView
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.util.getFavoriteIcon


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ArticleDetailScreen(
    appNavigator: AppNavigator,
    articlesViewModel: ArticleDetailsViewModel = hiltViewModel(),
) {

    val uiState = articlesViewModel.articleDetails.collectAsStateWithLifecycle()
    uiState.value?.let { article ->
        CollapsingToolbar(article, appNavigator) {
            articlesViewModel.changeArticleFavoriteState(article)
        }
    }

}

private val headerHeight = 250.dp

@Composable
fun CollapsingToolbar(
    article: ArticleUi,
    appNavigator: AppNavigator,
    onFavoriteButtonClicked: () -> Unit
) {
    val scroll: ScrollState = rememberScrollState(0)

    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { 56.dp.toPx() }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(article.imageUrl, scroll, headerHeightPx)
        Body(article, scroll)
        Toolbar(
            scroll, headerHeightPx, toolbarHeightPx, article.isFavorite, appNavigator,
            onFavoriteButtonClicked
        )
    }
}

@Composable
private fun Header(imageUrl: String?, scroll: ScrollState, headerHeightPx: Float) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(headerHeight)
        .graphicsLayer {
            translationY = -scroll.value.toFloat() / 2f // Parallax effect
            alpha = (-1f / headerHeightPx) * scroll.value + 1
        }
    ) {
        AsyncImage(
            model = imageUrl,
            error = painterResource(R.drawable.no_image_placeholder),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = "article image",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun Body(article: ArticleUi, scroll: ScrollState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(scroll)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Spacer(Modifier.height(headerHeight))
        article.publishedAt?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start),
                style = MaterialTheme.typography.overline,
                text = it
            )
        }
        Text(
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(vertical = 4.dp),
            text = article.title
        )
        article.author?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start),
                style = MaterialTheme.typography.caption,
                text = stringResource(R.string.published_by, it)
            )
        }
        Spacer(Modifier.height(16.dp))
        Divider()
        Spacer(Modifier.height(16.dp))

        Text(
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(vertical = 4.dp),
            text = article.content ?: ""
        )


        val context = LocalContext.current
        OutlinedButton(onClick = {
            launchWebView(context,article.articleUrl)
        }) {
            Text(text = stringResource(R.string.visit_orignal_article))
        }
    }


}

@Composable
private fun Toolbar(
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float,
    isFavorite: Boolean,
    appNavigator: AppNavigator,
    onFavoriteButtonClicked: () -> Unit
) {
    val toolbarBottom = headerHeightPx - toolbarHeightPx
    val showToolbar by remember {
        derivedStateOf {
            scroll.value >= toolbarBottom
        }
    }

    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { appNavigator.tryNavigateBack() },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .background(color = Color(0x59000000), shape = CircleShape)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        },
        title = {},
        actions = {
            IconButton(
                onClick = {
                    onFavoriteButtonClicked.invoke()
                }, modifier = Modifier
                    .padding(end = 12.dp)
                    .background(color = Color(0x59000000), shape = CircleShape)
                    .size(32.dp)
            ) {
                Icon(
                    painter = painterResource(getFavoriteIcon(isFavorite)),
                    contentDescription = stringResource(id = R.string.favorite_icon)
                )
            }
        },
        backgroundColor = if (showToolbar) MaterialTheme.colors.background
        else Color.Transparent,
        elevation = 0.dp
    )

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsAppTheme {
        // CollapsingToolbarParallaxEffect()
    }
}