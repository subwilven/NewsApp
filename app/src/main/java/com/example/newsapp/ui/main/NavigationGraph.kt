package com.example.newsapp.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.newsapp.navigation.Destination
import com.example.newsapp.ui.components.MyNavHost
import com.example.newsapp.ui.components.composable
import com.example.newsapp.ui.screens.articleDetail.ArticleDetailScreen
import com.example.newsapp.ui.screens.articles.ArticlesScreen
import com.example.newsapp.ui.screens.favorite.MyFavoritesScreen
import com.example.newsapp.util.ARG_ARTICLE_ID


@Composable
fun NavigationGraph(modifier :Modifier = Modifier,
                    navController: NavHostController,
                    showToolbarAndBottomBar: MutableState<Boolean>) {
    MyNavHost(navController, startDestination = Destination.ArticlesScreen,modifier) {
        composable(Destination.ArticlesScreen) {
            showToolbarAndBottomBar.value = true
            ArticlesScreen()
        }
        composable(Destination.FavoritesScreen) {
            showToolbarAndBottomBar.value = true
            MyFavoritesScreen()

        }
        composable(
            Destination.ArticleDetailsScreen ,
            arguments = listOf(navArgument(name = ARG_ARTICLE_ID) {
                type = NavType.IntType
            })
        ) {
            showToolbarAndBottomBar.value = false
            ArticleDetailScreen()

        }
    }
}
