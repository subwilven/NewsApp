package com.example.newsapp.ui.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.newsapp.model.BottomNavItem
import org.w3c.dom.Text

@Composable
fun RowScope.BottomNavigationItemView(
    item: BottomNavItem,
    currentRoute: String?,
    navController: NavController
)  {
     BottomNavigationItem(
        icon = bottomNavItemIcon(item.title,item.icon),
        label = bottomNavItemTitle(item.title),
        selectedContentColor = Color.Black,
        unselectedContentColor = Color.Black.copy(0.4f),
        alwaysShowLabel = true,
        selected = currentRoute == item.screen_route,
        onClick = {
            navController.navigate(item.screen_route) {

                navController.graph.startDestinationRoute?.let { screen_route ->
                    popUpTo(screen_route) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

@Composable
private fun bottomNavItemTitle(title :String) : @Composable (() -> Unit)  {
    return {Text(
        text = title,
        fontSize = 9.sp
    )
}}

@Composable
private fun bottomNavItemIcon(title :String,icon :Int) : @Composable (() -> Unit) {
    return { Icon(painterResource(id = icon), contentDescription = title) }
}

