package com.example.newsapp.ui.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.newsapp.model.BottomNavItem

@Composable
fun RowScope.BottomNavigationItemView(
    item: BottomNavItem,
    currentRoute: String?,
    onBottomBarItemClicked: (String) -> Unit,
)  {

    val title = stringResource(id = item.titleRes)
    NavigationBarItem(
        icon = { Icon(painterResource(id = item.icon), contentDescription = title) },
        selected = currentRoute == item.route,
        onClick = {
            onBottomBarItemClicked(item.route)
        }
    )
}

