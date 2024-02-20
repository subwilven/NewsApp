package com.example.newsapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.model.BottomNavItem
import com.example.newsapp.ui.main.BottomNavigationItemView

@Composable
fun MyBottomNavigationBar(
    bottomItems: List<BottomNavItem>,
    isVisible: Boolean,
    currentRoute: String,
    onBottomBarItemClicked: (String) -> Unit,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            NavigationBar(modifier = Modifier.height(56.dp)) {
                bottomItems.forEach { item ->
                    BottomNavigationItemView(item, currentRoute, onBottomBarItemClicked)
                }
            }
        }
    )

}
