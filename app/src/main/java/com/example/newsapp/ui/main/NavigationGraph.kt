package com.example.newsapp.ui.main

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.newsapp.navigation.AppNavigator
import com.example.newsapp.navigation.Destination
import com.example.newsapp.ui.screens.articleDetail.ArticleDetailScreen
import com.example.newsapp.ui.screens.articles.ArticlesScreen
import com.example.newsapp.ui.components.MyNavHost
import com.example.newsapp.ui.components.composable
import com.example.newsapp.ui.screens.favorite.MyFavoritesScreen
import com.example.newsapp.util.ARG_ARTICLE_ID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationGraph(navController: NavHostController,
                    appNavigator :AppNavigator,
                    modelBottomSheetState : ModalBottomSheetState,
                    showBottomSheet :(@Composable (ColumnScope.() -> Unit)) -> Unit) {
    MyNavHost(navController, startDestination = Destination.ArticlesScreen) {
        composable(Destination.ArticlesScreen) {
            ArticlesScreen(appNavigator,showBottomSheet,modelBottomSheetState)
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