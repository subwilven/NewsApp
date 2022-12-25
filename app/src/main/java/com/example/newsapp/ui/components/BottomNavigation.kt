package com.example.newsapp.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newsapp.model.BottomNavItem
import com.example.newsapp.ui.main.BottomNavigationItemView

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Articles,
        BottomNavItem.MyFavorites,
    )

    androidx.compose.material.BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItemView(item, currentRoute, navController)
        }
    }
}