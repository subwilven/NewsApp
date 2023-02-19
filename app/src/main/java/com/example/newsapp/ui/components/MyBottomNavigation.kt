package com.example.newsapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newsapp.model.BottomNavItem
import com.example.newsapp.navigation.AppNavigator
import com.example.newsapp.ui.main.BottomNavigationItemView

@Composable
fun MyBottomNavigation(
    navController: NavController,
    appNavigator: AppNavigator,
    isVisible : Boolean
) {
    val items = listOf(
        BottomNavItem.Articles,
        BottomNavItem.MyFavorites,
    )

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            NavigationBar(modifier = Modifier.height(56.dp)) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { item ->
                    BottomNavigationItemView(item, currentRoute, appNavigator)
                }
            }
        }
    )



}
