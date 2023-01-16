package com.example.newsapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        content = {
            TopAppBar(
                title = { Text("News App") },
            )
        }
    )
}
