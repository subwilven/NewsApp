package com.example.newsapp.ui.components

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
                containerColor = MaterialTheme.colorScheme.error,
                contentColor =  MaterialTheme.colorScheme.onError,
            )
        })
}
