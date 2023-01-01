package com.example.newsapp.ui.providers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.ui.components.LoadingFullScreen
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProvidersListContent(
    providersViewModel: ProvidersViewModel = hiltViewModel(),
//    providerList: List<ProviderUi>,
//    onSourceSelected: (ProviderUi, Int) -> Unit
) {

    val uiState: ProviderUiState by providersViewModel.uiState.collectAsState()
    val state = rememberScrollState()

    if(uiState.isLoading){
       val halfOfScreenHeight =  LocalConfiguration.current.screenHeightDp.div(2).dp
        Surface(modifier = Modifier.height(halfOfScreenHeight),) {
            LoadingFullScreen()
        }
    }else{
        FlowRow(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .verticalScroll(state),
            mainAxisSpacing = 4.dp,
        ) {

            uiState.providersList.onEachIndexed { index, sourceUi ->

                FilterChip(
                    selected = sourceUi.isSelected,
                    onClick = {
                      //  onSourceSelected.invoke(sourceUi, index)
                    },
                    border = BorderStroke(
                        ChipDefaults.OutlinedBorderSize,
                        Color.Red
                    ),
//                colors = ChipDefaults.chipColors(
//                    backgroundColor = Color.White,
//                    contentColor = Color.Red
//                ),

                ) {
                    Text(sourceUi.name)
                }
            }
        }
    }
}