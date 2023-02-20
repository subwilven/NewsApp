package com.example.newsapp.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
