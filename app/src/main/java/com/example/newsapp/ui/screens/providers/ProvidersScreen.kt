package com.example.newsapp.ui.screens.providers

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.R
import com.example.newsapp.model.providers.ProviderUi
import com.example.newsapp.ui.components.LoadingFullScreen
import com.example.newsapp.ui.components.MyFilterChip
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProvidersScreen(
    providersViewModel: ProvidersViewModel = hiltViewModel(),
    onProvidersSelected: (List<ProviderUi>) -> Unit
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
            providersViewModel.processActions(ProvidersActions.ResetChanges)
        }
    }

    ProvidersListContent(isLoading = uiState.isLoading,
        providersList = uiState.providersList,
        onProvidersSelected = { providerUi, index ->
            providersViewModel.processActions(
                ProvidersActions.ToggleProviderSelection(
                    providerUi, index
                )
            )
        },)
}

@Composable
private fun ProvidersListContent(
    isLoading: Boolean,
    providersList: List<ProviderUi>,
    onProvidersSelected: (ProviderUi, Int) -> Unit,
) {
    if (isLoading) {
        LoadingFullScreen(modifier = Modifier)
    } else {
        ProvidersChips(providersList, onProvidersSelected)
    }
}

@Composable
private fun ProvidersChips(
    providersList: List<ProviderUi>, onChipClicked: (ProviderUi, Int) -> Unit
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

@Composable
private fun ApplyFilterButton(text: String, modifier: Modifier = Modifier, onButtonClicked: () -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = onButtonClicked
    ) {
        Text(text = text)
    }
}


