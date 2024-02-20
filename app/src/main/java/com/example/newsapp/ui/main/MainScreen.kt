package com.example.newsapp.ui.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.newsapp.ui.components.MyBottomNavigationBar
import com.example.newsapp.ui.components.MySnackHost
import com.example.newsapp.ui.components.MyTopAppBar
import com.example.newsapp.ui.states.rememberAppState
import com.example.newsapp.ui.theme.NewsAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreenView() {

    val appState = rememberAppState()
    val systemUiController = rememberSystemUiController()
    val snackbarHostState = remember { SnackbarHostState() }

    systemUiController.setSystemBarsColor(
        color = Color.Transparent,
        darkIcons = isSystemInDarkTheme().not()
    )

    NewsAppTheme {

            Scaffold(
                topBar = { MyTopAppBar(appState.shouldShowAppBar) },
                snackbarHost = { MySnackHost(snackbarHostState) },
                bottomBar = {
                    MyBottomNavigationBar(
                        appState.bottomBarTabs,
                        appState.shouldShowBottomBar,
                        appState.currentRoute,
                        appState::navigateToBottomBarRoute
                    )
                },
                content = { padding ->

                        NavigationGraph(
                            navController = appState.navController,
                            onShowSnackBar = {
                                snackbarHostState.showSnackbar(
                                    message = it,
                                    duration = SnackbarDuration.Short,
                                )
                            },
                            modifier = Modifier.padding(
                                bottom = padding.calculateBottomPadding(),
                                top = padding.calculateTopPadding(),
                            ),
                        )

                }
            )
    }
}
