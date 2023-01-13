package com.example.newsapp.ui.screens.providers

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.R
import com.example.newsapp.model.providers.ProviderUi
import com.example.newsapp.ui.components.LoadingFullScreen
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun ProvidersScreen(
    modelBottomSheetState: ModalBottomSheetState,
    providersViewModel: ProvidersViewModel = hiltViewModel(),
    onProvidersSelected: (List<ProviderUi>) -> Unit
) {

    val uiState: ProviderUiState by providersViewModel.uiState.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(modelBottomSheetState.isVisible) {
        if (!modelBottomSheetState.isVisible) {
            providersViewModel.processActions(ProvidersActions.ResetChanges)
        }
    }

    LaunchedEffect(uiState.onFiltrationProcessDone) {
        if (uiState.onFiltrationProcessDone) {
            modelBottomSheetState.hide()
            onProvidersSelected(providersViewModel.getSelectedProviders())
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
        onClearButtonClicked = {
            providersViewModel.processActions(ProvidersActions.ClearFilter)
        },
        onFilterButtonClicked = {
            providersViewModel.processActions(ProvidersActions.SubmitFilter)
        },
        onCloseClicked = {
            coroutineScope.launch {
                modelBottomSheetState.hide()
            }
        }
    )
}

@Composable
fun ProvidersListContent(
    isLoading: Boolean,
    providersList: List<ProviderUi>,
    onProvidersSelected: (ProviderUi, Int) -> Unit,
    onFilterButtonClicked: () -> Unit,
    onClearButtonClicked: () -> Unit,
    onCloseClicked: () -> Unit
) {
    if (isLoading) {
        val halfOfScreenHeight = LocalConfiguration.current.screenHeightDp.div(2).dp
        LoadingFullScreen(modifier = Modifier.height(halfOfScreenHeight))
    } else {
        Column {
            Toolbar(onCloseClicked,onClearButtonClicked)
            Box {
                ProvidersChips(providersList, onProvidersSelected)
                ApplyFilterButton(
                    stringResource(id = R.string.apply),
                    Modifier.align(Alignment.BottomCenter)
                ) {
                    onFilterButtonClicked.invoke()
                }
            }
        }

    }
}

@Composable
private fun Toolbar(
    onCloseClicked: () -> Unit,
    onClearClicked: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            TextButton(
                onClick = {  onClearClicked.invoke()  },
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.clear)
                )
            }
        },
        title = {},
        actions = {
            IconButton(
                onClick = {
                    onCloseClicked.invoke()
                }, modifier = Modifier
                    .padding(end = 12.dp)
                    .size(32.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close_24),
                    contentDescription = null
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background
    )

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
fun ApplyFilterButton(text: String, modifier: Modifier = Modifier, onButtonClicked: () -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = onButtonClicked
    ) {
        Text(text = text)
    }
}


