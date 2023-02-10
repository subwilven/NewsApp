package com.example.newsapp.ui.screens.providers

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.R
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.ui.components.LoadingFullScreen
import com.example.newsapp.ui.components.MyDialog
import com.example.newsapp.ui.components.MyFilterChip
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ProvidersScreen(
    providersViewModel: ProvidersViewModel = hiltViewModel(),
    dialogState: MutableState<Boolean>, ) {

    MyDialog(
        R.string.select_providers,
        R.string.apply_filter,
        R.string.reset,
        dialogState,
        onPositiveClicked = providersViewModel::applyFilter,
        onNegativeClicked = providersViewModel::resetFilter
    ) {
        val uiState: ProviderUiState by providersViewModel.uiState.collectAsStateWithLifecycle()
        val lifeCycleOwner =LocalLifecycleOwner.current

        DisposableEffect(lifeCycleOwner) {
            onDispose {
                providersViewModel.resetUserChanges()
            }
        }

        ProvidersListContent(
            isLoading = uiState.isLoading,
            providersList = uiState.providersList,
            onProvidersSelected = providersViewModel::toggleProviderSelectionState
        )
    }
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



