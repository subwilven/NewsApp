package com.example.newsapp.ui.main

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.navigation.AppNavigatorImpl
import com.example.newsapp.navigation.NavigationEffects
import com.example.newsapp.ui.components.MySnackHost
import com.example.newsapp.ui.components.MyTopAppBar
import com.example.newsapp.ui.components.MyBottomNavigation
import com.example.newsapp.ui.theme.NewsAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch


//var currentBottomSheetContent: @Composable  (ColumnScope.() -> Unit) = { Text("") }

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    val appNavigator = AppNavigatorImpl
//    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val systemUiController = rememberSystemUiController()
//    val bottomSheetState = rememberModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Hidden,
//        skipHalfExpanded = true,
//        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
//    )
    val coroutineScope = rememberCoroutineScope()
    val showToolbarAndBottomBar: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) }
    val snackbarHostState = remember { SnackbarHostState() }

    systemUiController.setSystemBarsColor(
        color = Color.Transparent,
        darkIcons = isSystemInDarkTheme().not()
    )
//    BackHandler(bottomSheetState.isVisible) {
//        coroutineScope.launch { bottomSheetState.hide() }
//    }

    NavigationEffects(
        navigationChannel = appNavigator.navigationChannel,
        navHostController = navController
    )

    NewsAppTheme {

        Scaffold(
            topBar = { MyTopAppBar(showToolbarAndBottomBar.value) },
            snackbarHost = { MySnackHost(snackbarHostState) },
            bottomBar = {
                MyBottomNavigation(
                    navController = navController,
                    appNavigator,
                    showToolbarAndBottomBar.value
                )
            },
            content = { _ ->
                NavigationGraph(
                    navController = navController,
                    appNavigator,
                    showToolbarAndBottomBar
                )
            }
        )
    }
}