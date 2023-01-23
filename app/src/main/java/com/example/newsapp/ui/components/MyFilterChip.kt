package com.example.newsapp.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFilterChip(isSelected : Boolean ,
                 label:String,onSelectedChange : () -> Unit){
    FilterChip(
        label = {
            Text(label)
        },
        selected = isSelected,
        onClick = {
            onSelectedChange()
        },
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.onBackground,
            selectedBorderColor = MaterialTheme.colorScheme.primary,
        ),
        colors = FilterChipDefaults.filterChipColors(
            labelColor = MaterialTheme.colorScheme.onBackground,
            selectedContainerColor = MaterialTheme.colorScheme.inversePrimary,
            selectedLabelColor = MaterialTheme.colorScheme.onBackground,
        )
    )

}