package com.example.newsapp.ui.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.model.articles.ArticleUi
import com.example.newsapp.navigation.AppNavigator
import com.example.newsapp.navigation.Destination
import com.example.newsapp.navigation.navigateToArticleDetails
import com.example.newsapp.ui.main.LocalAppNavigator

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MyFavoritesScreen(
    myFavoritesViewModel: MyFavoritesViewModel = hiltViewModel(),
) {
    val uiState = myFavoritesViewModel
        .uiState
        .collectAsStateWithLifecycle()
    val appNavigator = LocalAppNavigator.current
    LazyColumn {
        items(uiState.value.favoriteArticles.count()) { index ->
            uiState.value.favoriteArticles[index].let {
                FavoriteArticleItem(it){ article ->
                    navigateToArticleDetails(appNavigator,article)
                }

            }
        }
    }
}


@Composable
private fun FavoriteArticleItem(article : ArticleUi,onArticleClicked : (ArticleUi) -> Unit ){

    Row(Modifier.padding(vertical = 8.dp, horizontal = 16.dp).clickable {
        onArticleClicked.invoke(article)
    }){
        Column(modifier = Modifier.weight(0.7f)){
            Text(
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(vertical = 4.dp).padding(end = 8.dp),
                text = article.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(vertical = 4.dp).padding(end = 8.dp),
                text = article.description ?: "",
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

        }

        AsyncImage(
            model = article.imageUrl,
            error = painterResource(R.drawable.no_image_placeholder),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = article.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .height(75.dp)
                .width(75.dp)
                .align(Alignment.CenterVertically)
        )

    }
    Divider()
}
