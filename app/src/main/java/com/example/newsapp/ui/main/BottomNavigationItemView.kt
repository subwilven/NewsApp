package com.example.newsapp.ui.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon

import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.newsapp.model.BottomNavItem
import com.example.newsapp.navigation.AppNavigator
import com.example.newsapp.navigation.AppNavigatorImpl
import org.w3c.dom.Text

@Composable
fun RowScope.BottomNavigationItemView(
    item: BottomNavItem,
    currentRoute: String?,
    appNavigator: AppNavigator
)  {
    val title = stringResource(id = item.titleRes)
    NavigationBarItem(
        icon = bottomNavItemIcon(title,item.icon),
        selected = currentRoute == item.destination.fullRoute,
        onClick = {
            appNavigator.tryNavigateToBottomBarScreen(item.destination.fullRoute)
        }
    )
}

@Composable
private fun bottomNavItemTitle(title :String) : @Composable (() -> Unit)  {
    return {
        Text(
        text = title,
        fontSize = 9.sp
    )
}}

@Composable
private fun bottomNavItemIcon(title :String,icon :Int) : @Composable (() -> Unit) {
    return { Icon(painterResource(id = icon), contentDescription = title) }
}

