package com.example.newsapp.ui.screens.providers

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.ui.components.LoadingFullScreen
import com.example.newsapp.ui.components.MyFilterChip
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProvidersScreen(
    providersViewModel: ProvidersViewModel = hiltViewModel(),
    onProvidersSelected: (List<Provider>) -> Unit
) {

    val uiState: ProviderUiState by providersViewModel.uiState.collectAsStateWithLifecycle()

    val lifeCycleOwner =LocalLifecycleOwner.current


    LaunchedEffect(uiState.onFiltrationProcessDone) {
        if (uiState.onFiltrationProcessDone) {
            onProvidersSelected(providersViewModel.getSelectedProviders())
        }
    }

    DisposableEffect(lifeCycleOwner) {
        onDispose {
            providersViewModel.resetUserChanges()
        }
    }

    ProvidersListContent(isLoading = uiState.isLoading,
        providersList = uiState.providersList,
        onProvidersSelected = providersViewModel::emitToggleSelectionFlow
    )
}

@Composable
private fun ProvidersListContent(
    isLoading: Boolean,
    providersList: List<Provider>,
    onProvidersSelected: (Provider, Int) -> Unit,
) {
    if (isLoading) {
        LoadingFullScreen(modifier = Modifier)
    } else {
        ProvidersChips(providersList, onProvidersSelected)
    }
}

@Composable
private fun ProvidersChips(
    providersList: List<Provider>, onChipClicked: (Provider, Int) -> Unit
) {
    val scrollState = rememberScrollState()
    FlowRow(
        crossAxisSpacing = 0.dp,
        mainAxisSpacing = 4.dp,
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(scrollState),
    ) {
        providersList.onEachIndexed { index, sourceUi ->
            MyFilterChip(
                sourceUi.isSelected,
                sourceUi.name
            ) {
                onChipClicked(sourceUi, index)
            }
        }

    }
}



