package com.example.newsapp.ui.main

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsapp.navigation.AppNavigator
import com.example.newsapp.navigation.AppNavigatorImpl
import com.example.newsapp.navigation.Destination
import com.example.newsapp.ui.articleDetail.ArticleDetailScreen
import com.example.newsapp.ui.articles.ArticlesScreen
import com.example.newsapp.ui.components.MyNavHost
import com.example.newsapp.ui.components.composable
import com.example.newsapp.ui.favorite.MyFavoritesScreen
import com.example.newsapp.util.ARG_ARTICLE_ID
import com.example.newsapp.util.NewsAppScreens

@Composable
fun NavigationGraph(navController: NavHostController,appNavigator :AppNavigator, showBottomSheet :(@Composable (ColumnScope.() -> Unit)) -> Unit) {
    MyNavHost(navController, startDestination = Destination.ArticlesScreen) {
        composable(Destination.ArticlesScreen) {
            ArticlesScreen(appNavigator,showBottomSheet)
        }
        composable(Destination.FavoritesScreen) {
            MyFavoritesScreen(appNavigator)
        }
        composable(
            Destination.ArticleDetailsScreen ,
            arguments = listOf(navArgument(name = ARG_ARTICLE_ID) {
                type = NavType.IntType
            })
        ) {
            ArticleDetailScreen()
        }
    }
}