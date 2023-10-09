package com.example.newsapp.ui.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.R
import com.example.newsapp.model.articles.Article
import com.example.newsapp.navigation.navigateToArticleDetails
import com.example.newsapp.ui.components.EmptyScreen
import com.example.newsapp.ui.components.LoadingAsyncImage
import com.example.newsapp.ui.components.LoadingFullScreen
import com.example.newsapp.ui.main.LocalAppNavigator

@Composable
fun MyFavoritesScreen(
    myFavoritesViewModel: MyFavoritesViewModel = hiltViewModel(),
) {
    val uiState = myFavoritesViewModel
        .uiState
        .collectAsStateWithLifecycle()
    val appNavigator = LocalAppNavigator.current
    if (uiState.value.isLoading) {
        LoadingFullScreen()
    } else if (uiState.value.favoriteArticles.isEmpty()) {
        EmptyScreen(stringResource(id = R.string.no_favorites))
    } else {
        LazyColumn {
            items(uiState.value.favoriteArticles.count()) { index ->
                FavoriteArticleItem(uiState.value.favoriteArticles[index]) { article ->
                    navigateToArticleDetails(appNavigator, article)
                }
            }
        }
    }

}


@Composable
private fun FavoriteArticleItem(article: Article, onArticleClicked: (Article) -> Unit) {

    Row(
        Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable {
                onArticleClicked.invoke(article)
            }) {
        Column(modifier = Modifier.weight(0.7f)) {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .padding(end = 8.dp),
                text = article.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .padding(end = 8.dp),
                text = article.description,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

        }
        LoadingAsyncImage(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .height(75.dp)
                .width(75.dp)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop,
            imageUrl = article.imageUrl
        )
    }
    Divider()
}
