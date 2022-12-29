package com.example.newsapp.ui.components

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MySnackHost(state: SnackbarHostState) {
    SnackbarHost(
        state,
        modifier = Modifier
            .systemBarsPadding()
            .wrapContentWidth(align = Alignment.Start),
        snackbar = { data ->
            Snackbar(
                data,
                backgroundColor = Color(0x99000000),
                elevation = 1.dp
            )
        })
}