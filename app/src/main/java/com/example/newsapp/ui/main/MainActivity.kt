package com.example.newsapp.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.model.BottomNavItem
import com.example.newsapp.ui.favorite.MyFavoritesScreen
import com.example.newsapp.ui.articles.ArticlesScreen
import com.example.newsapp.R
import com.example.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreenView()
        }
    }
}

val LocalScaffoldState = compositionLocalOf<ScaffoldState> { error("ScaffoldState not set") }


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    NewsAppTheme {
        Scaffold(scaffoldState = scaffoldState,
            topBar = { MyTopAppBar() },
            snackbarHost = { state -> MySnackHost(state) },
            bottomBar = { BottomNavigation(navController = navController) }
        ) {
            CompositionLocalProvider(LocalScaffoldState provides scaffoldState) {
                NavigationGraph(navController = navController)
            }
        }
    }

}

@Composable
fun MyTopAppBar() {
    TopAppBar(title = { Text("Top AppBar") },
        backgroundColor = Color(0xFF008800),
    )
}

@Composable
fun MySnackHost(state: SnackbarHostState) {
    SnackbarHost(
        state,
        snackbar = { data ->
            Snackbar(
                data,
                backgroundColor = Color(0x99000000),
                elevation = 1.dp
            )
        })
}
@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Articles,
        BottomNavItem.MyFavorites,
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.teal_200),
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItemView(item,currentRoute,navController)
        }
    }
}

@Composable
fun NavigationGraph( navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Articles.screen_route) {
        composable(BottomNavItem.Articles.screen_route) {
            ArticlesScreen()
        }
        composable(BottomNavItem.MyFavorites.screen_route) {
            MyFavoritesScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    MainScreenView()
}