package com.example.newsapp.ui.screens.providers

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.example.newsapp.R
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.ui.components.LoadingFullScreen
import com.example.newsapp.ui.components.MyDialog
import com.example.newsapp.ui.components.MyFilterChip
import com.example.newsapp.util.Constants.Destinations.PROVIDERS
import com.google.accompanist.flowlayout.FlowRow

fun NavController.navigateToProviders() {
    this.navigate(PROVIDERS) {
        launchSingleTop = true
    }
}


fun NavGraphBuilder.ProvidersDialog(
    onBackClick: () -> Unit,
    onShowSnackBar: suspend (String) -> Unit,
) {
    dialog(
        route = PROVIDERS,
    ) {
        ProvidersRoute(onBackClick, onShowSnackBar)
    }
}


@Composable
private fun ProvidersRoute(
    onBackClick: () -> Unit,
    onShowSnackBar: suspend (String) -> Unit,
    providersViewModel: ProvidersViewModel = hiltViewModel(),
) {

    MyDialog(
        R.string.providers_filter,
        R.string.apply,
        R.string.reset,
        onBackClick,
        onPositiveClicked = providersViewModel::applyFilter,
        onNegativeClicked = providersViewModel::resetFilter
    ) {
        val uiState: ProviderUiState by providersViewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = uiState.errorMessage) {
            uiState.errorMessage?.let {
                onShowSnackBar(it)
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
fun ProvidersListContent(
    isLoading: Boolean,
    providersList: List<Provider>,
    onProvidersSelected: (Provider) -> Unit,
) {
    if (isLoading) {
        LoadingFullScreen(modifier = Modifier)
    } else {
        ProvidersChips(providersList, onProvidersSelected)
    }
}

@Composable
private fun ProvidersChips(
    providersList: List<Provider>, onChipClicked: (Provider) -> Unit
) {
    val scrollState = rememberScrollState()
    FlowRow(
        crossAxisSpacing = 0.dp,
        mainAxisSpacing = 4.dp,
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(scrollState),
    ) {
        providersList.onEach { sourceUi ->
            MyFilterChip(
                sourceUi.isSelected,
                sourceUi.name
            ) {
                onChipClicked(sourceUi)
            }
        }

    }
}
