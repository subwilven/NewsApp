package com.example.newsapp.ui.main

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.navigation.AppNavigatorImpl
import com.example.newsapp.navigation.NavigationEffects
import com.example.newsapp.ui.components.MySnackHost
import com.example.newsapp.ui.components.MyTopAppBar
import com.example.newsapp.ui.components.BottomNavigation
import com.example.newsapp.ui.theme.NewsAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch


var currentBottomSheetContent: @Composable  (ColumnScope.() -> Unit) = { Text("") }

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    val appNavigator = AppNavigatorImpl
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val systemUiController = rememberSystemUiController()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent,
        darkIcons = isSystemInDarkTheme().not()
    )
    BackHandler(bottomSheetState.isVisible) {
        coroutineScope.launch { bottomSheetState.hide() }
    }

    NavigationEffects(
        navigationChannel = appNavigator.navigationChannel,
        navHostController = navController
    )
    NewsAppTheme {
        ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetContent = currentBottomSheetContent ){
            Scaffold(scaffoldState = scaffoldState,
                topBar = { MyTopAppBar() },
                snackbarHost = { state -> MySnackHost(state) },
                bottomBar = { BottomNavigation(navController = navController,appNavigator) }
            ) {
//            CompositionLocalProvider(LocalScaffoldState provides scaffoldState) {
                NavigationGraph(navController = navController,appNavigator,bottomSheetState){
                    currentBottomSheetContent = it
                    coroutineScope.launch { bottomSheetState.show() }
                }
//            }
            }
        }
    }
}