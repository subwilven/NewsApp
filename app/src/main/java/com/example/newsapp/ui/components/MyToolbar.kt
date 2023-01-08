package com.example.newsapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun MyTopAppBar(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        content = {
            TopAppBar(
                title = { Text("News App") },
                backgroundColor = MaterialTheme.colors.background,
            )
        }
    )
}
