package com.example.newsapp.ui.screens.articleDetail


import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.R
import com.example.newsapp.model.articles.Article
import com.example.newsapp.navigation.launchWebView
import com.example.newsapp.ui.components.ArticleImage
import com.example.newsapp.ui.components.FavoriteButton
import com.example.newsapp.ui.main.LocalAppNavigator
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.darkOnBackground
import com.example.newsapp.ui.theme.shadow


@Composable
fun ArticleDetailScreen(
    articlesViewModel: ArticleDetailsViewModel = hiltViewModel(),
) {

    val uiState = articlesViewModel.articleDetails.collectAsStateWithLifecycle()
    uiState.value?.let { article ->
        CollapsingToolbar(
            article,
            onFavoriteButtonClicked = articlesViewModel::toggleArticleFavoriteState
        )
    }

}

private val headerHeight = 250.dp

@Composable
fun CollapsingToolbar(
    article: Article,
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
            scroll, headerHeightPx, toolbarHeightPx, article.isFavorite,
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
        ArticleImage(
            imageUrl = imageUrl,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun Body(article: Article, scroll: ScrollState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(scroll)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Spacer(Modifier.height(headerHeight))
        article.publishDate?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start),
                style = MaterialTheme.typography.labelMedium,
                text = it
            )
        }
        Text(
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 4.dp),
            text = article.title
        )
        article.author?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start),
                style = MaterialTheme.typography.labelSmall,
                text = stringResource(R.string.published_by, it)
            )
        }
        Spacer(Modifier.height(16.dp))
        Divider()
        Spacer(Modifier.height(16.dp))

        repeat(5) {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 4.dp),
                text = article.content ?: ""
            )

        }
        val context = LocalContext.current
        OutlinedButton(onClick = {
            launchWebView(context, article.articleUrl)
        }) {
            Text(text = stringResource(R.string.visit_original_article))
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float,
    isFavorite: Boolean,
    onFavoriteButtonClicked: () -> Unit
) {
    val toolbarBottom = headerHeightPx - toolbarHeightPx
    val showToolbar by remember {
        derivedStateOf {
            scroll.value >= toolbarBottom
        }
    }
    val appNavigator = LocalAppNavigator.current
    //do not follow dark/light theme because we have a dark background
    val iconTint = darkOnBackground
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { appNavigator.tryNavigateBack() },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .background(
                        color = shadow,
                        shape = CircleShape
                    )
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    tint = iconTint
                )
            }
        },
        title = {},
        actions = {
            FavoriteButton(
                Modifier
                    .background(
                        color = shadow,
                        shape = CircleShape
                    )
                    .padding(4.dp),
                isFavorite,
                iconTint
            ) {
                onFavoriteButtonClicked.invoke()
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = if (showToolbar) MaterialTheme.colorScheme.inverseOnSurface
            else Color.Transparent,
        ),
    )

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsAppTheme {
        // CollapsingToolbarParallaxEffect()
    }
}
