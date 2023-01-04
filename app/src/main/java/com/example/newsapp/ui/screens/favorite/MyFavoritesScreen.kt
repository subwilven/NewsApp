package com.example.newsapp.ui.screens.favorite

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.navigation.AppNavigator

@Composable
fun MyFavoritesScreen(
    appNavigator: AppNavigator,
    myFavoritesViewModel: MyFavoritesViewModel = hiltViewModel(),
) {
//    val favoritesList = myFavoritesViewModel
//        .favoritesArticles
//        .collectAsState(initial = listOf())
//
//    LazyColumn {
//        items(favoritesList.value.count()) { index ->
//            favoritesList.value[index].let {
////                ArticleItem(it, {
////                    navController.navigate(NewsAppScreens.ArticleDetailsScreen.route + "/${it.id}")
////                },{ myFavoritesViewModel.changeArticleFavoriteState(it) })
////                Divider(color = Color.LightGray, thickness = 1.dp)
//            }
//        }
//    }



}
