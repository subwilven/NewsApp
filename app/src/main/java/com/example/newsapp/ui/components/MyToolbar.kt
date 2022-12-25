package com.example.newsapp.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun MyTopAppBar() {
    TopAppBar(
        title = { Text("News App") },
        backgroundColor = MaterialTheme.colors.background,
    )
}
