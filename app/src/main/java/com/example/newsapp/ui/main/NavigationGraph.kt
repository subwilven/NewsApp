package com.example.newsapp.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.newsapp.ui.components.MyNavHost
import com.example.newsapp.ui.screens.articleDetail.ArticleDetailScreen
import com.example.newsapp.ui.screens.articleDetail.navigateToArticleDetail
import com.example.newsapp.ui.screens.articles.ArticlesScreen
import com.example.newsapp.ui.screens.favorite.MyFavoritesScreen
import com.example.newsapp.ui.screens.providers.ProvidersDialog
import com.example.newsapp.ui.screens.providers.navigateToProviders
import com.example.newsapp.util.Constants.Destinations.ARTICLES


@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onShowSnackBar: suspend (String) -> Unit,
) {
    MyNavHost(navController, startDestination = ARTICLES, modifier) {

        ArticlesScreen(
            onShowSnackBar,
            navController::navigateToArticleDetail,
            navController::navigateToProviders
        )

        MyFavoritesScreen(navController::navigateToArticleDetail)
        ProvidersDialog(navController::popBackStack, onShowSnackBar)
        ArticleDetailScreen(navController::popBackStack)
    }
}
