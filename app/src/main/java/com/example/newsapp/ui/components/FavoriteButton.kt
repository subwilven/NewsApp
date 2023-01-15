package com.example.newsapp.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.screens.articles.ArticlesActions
import com.example.newsapp.util.getFavoriteIcon

@Composable
fun Favoritebutton(modifier: Modifier,isChecked :Boolean, onCheckedChange: (Boolean) -> Unit,){
    IconToggleButton(
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.padding(4.dp)){
        val transition = updateTransition(isChecked, label = "Checked indicator")

        val tint by transition.animateColor(
            label = "Tint"
        ) { isChecked ->
            if (isChecked) Color.Red else Color.Black
        }

        val size by transition.animateDp(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    keyframes {
                        durationMillis = 250
                        30.dp at 0 with LinearOutSlowInEasing // for 0-15 ms
                        35.dp at 15 with FastOutLinearInEasing // for 15-75 ms
                        40.dp at 75 // ms
                        35.dp at 150 // ms
                    }
                } else {
                    spring(stiffness = Spring.StiffnessVeryLow)
                }
            },
            label = "Size"
        ) { 30.dp }

        Icon(
            imageVector = getFavoriteIcon(isChecked),
            contentDescription = null,
            tint = tint,
            modifier = androidx.compose.ui.Modifier.size(size)
        )
    }
}