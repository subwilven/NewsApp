package com.example.newsapp.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FavoriteButton(modifier: Modifier, isChecked :Boolean,
                   color : Color,
                   onCheckedChange: (Boolean) -> Unit,){
    IconToggleButton(
        checked = isChecked,
        onCheckedChange = onCheckedChange){
        val transition = updateTransition(isChecked, label = "Checked indicator")

        val size by transition.animateDp(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    keyframes {
                        durationMillis = 350
                        25.dp at 0 with LinearOutSlowInEasing // for 0-15 ms
                        32.dp at 50 with FastOutLinearInEasing // for 15-75 ms
                        38.dp at 150 // ms
                        32.dp at 250 // ms
                    }
                } else {
                    spring(stiffness = Spring.StiffnessVeryLow)
                }
            },
            label = "Size"
        ) { 24.dp }

        Icon(
            imageVector = getFavoriteIcon(isChecked),
            contentDescription = null,
            tint = color ,
            modifier = modifier.size(size)
        )
    }
}

private  fun getFavoriteIcon(isFavorite: Boolean) = if (isFavorite) Icons.Filled.Favorite
else Icons.Filled.FavoriteBorder
