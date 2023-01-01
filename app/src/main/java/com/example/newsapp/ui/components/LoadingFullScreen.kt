package com.example.newsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingFullScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}