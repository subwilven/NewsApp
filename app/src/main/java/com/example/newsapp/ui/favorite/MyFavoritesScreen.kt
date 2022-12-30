package com.example.newsapp.ui.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsapp.R
import com.example.newsapp.navigation.AppNavigator
import com.example.newsapp.navigation.AppNavigatorImpl
import com.example.newsapp.ui.articles.ArticleItem
import com.example.newsapp.ui.articles.ArticlesViewModel
import com.example.newsapp.util.NewsAppScreens

@Composable
fun MyFavoritesScreen(
    appNavigator: AppNavigator,
    myFavoritesViewModel: MyFavoritesViewModel = hiltViewModel(),
) {
    val favoritesList = myFavoritesViewModel
        .favoritesArticles
        .collectAsState(initial = listOf())

    LazyColumn {
        items(favoritesList.value.count()) { index ->
            favoritesList.value[index].let {
//                ArticleItem(it, {
//                    navController.navigate(NewsAppScreens.ArticleDetailsScreen.route + "/${it.id}")
//                },{ myFavoritesViewModel.changeArticleFavoriteState(it) })
//                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }



}
