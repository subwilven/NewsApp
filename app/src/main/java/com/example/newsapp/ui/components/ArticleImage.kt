package com.example.newsapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.newsapp.R

@Composable
fun ArticleImage(modifier: Modifier, contentScale: ContentScale, imageUrl: String?) {
    AsyncImage(
        model = imageUrl,
        error = painterResource(R.drawable.no_image_placeholder),
        placeholder = painterResource(R.drawable.placeholder),
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier
    )
}
