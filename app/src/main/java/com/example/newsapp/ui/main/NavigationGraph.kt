package com.example.newsapp.ui.main

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsapp.ui.articleDetail.ArticleDetailScreen
import com.example.newsapp.ui.articles.ArticlesScreen
import com.example.newsapp.ui.favorite.MyFavoritesScreen
import com.example.newsapp.util.ARG_ARTICLE_ID
import com.example.newsapp.util.NewsAppScreens

@Composable
fun NavigationGraph(navController: NavHostController, showBottomSheet :(@Composable (ColumnScope.() -> Unit)) -> Unit) {
    NavHost(navController, startDestination = NewsAppScreens.ArticlesScreen.route) {
        composable(NewsAppScreens.ArticlesScreen.route) {
            ArticlesScreen(navController,showBottomSheet)
        }
        composable(NewsAppScreens.FavoritesScreen.route) {
            MyFavoritesScreen(navController)
        }
        composable(
            NewsAppScreens.ArticleDetailsScreen.route + "/{$ARG_ARTICLE_ID}",
            arguments = listOf(navArgument(name = ARG_ARTICLE_ID) {
                type = NavType.IntType
            })
        ) {
            ArticleDetailScreen()
        }
    }
}