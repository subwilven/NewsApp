package com.example.newsapp.ui.screens.providers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.model.providers.ProviderUi
import com.example.newsapp.ui.components.LoadingFullScreen
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun ProvidersScreen(
    modelBottomSheetState: ModalBottomSheetState,
    providersViewModel: ProvidersViewModel = hiltViewModel(),
    onProvidersSelected: (List<ProviderUi>) -> Unit
) {

    val uiState: ProviderUiState by providersViewModel.uiState.collectAsStateWithLifecycle()


    LaunchedEffect(modelBottomSheetState.isVisible) {
        if (!modelBottomSheetState.isVisible) {
            providersViewModel.processActions(ProvidersActions.ResetChanges)
        }
    }

    LaunchedEffect(uiState.shouldNavigateBack) {
        if(uiState.shouldNavigateBack){
            onProvidersSelected(uiState.selectedProvidersList)
            modelBottomSheetState.hide()
        }
    }

    ProvidersListContent(
        isLoading = uiState.isLoading,
        providersList = uiState.providersList,
        onProvidersSelected = { providerUi, index ->
            providersViewModel.processActions(
                ProvidersActions.ToggleProviderSelection(
                    providerUi,
                    index
                )
            )
        },
        onFilterButtonClicked = {
           providersViewModel.processActions(ProvidersActions.SubmitFilter)
        }
    )
}

@Composable
fun ProvidersListContent(
    isLoading: Boolean,
    providersList: List<ProviderUi>,
    onProvidersSelected: (ProviderUi, Int) -> Unit,
    onFilterButtonClicked: () -> Unit
) {
    if (isLoading) {
        val halfOfScreenHeight = LocalConfiguration.current.screenHeightDp.div(2).dp
        LoadingFullScreen(modifier = Modifier.height(halfOfScreenHeight))
    } else {
        Box {
            ProvidersChips(providersList, onProvidersSelected)
            ApplyFilterButton(
                Modifier.align(Alignment.BottomCenter),
                onFilterButtonClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProvidersChips(
    providersList: List<ProviderUi>,
    onChipClicked: (ProviderUi, Int) -> Unit
) {
    val scrollState = rememberScrollState()
    FlowRow(
        mainAxisSpacing = 4.dp,
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 50.dp)
            .verticalScroll(scrollState),
    ) {
        providersList.onEachIndexed { index, sourceUi ->
            FilterChip(
                selected = sourceUi.isSelected,
                onClick = {
                    onChipClicked(sourceUi, index)
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

@Composable
fun ApplyFilterButton(modifier: Modifier = Modifier, onButtonClicked: () -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = onButtonClicked
    ) {
        Text(text = "Apply filter")
    }
}


