package com.example.newsapp.ui.screens.providers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.providers.ProviderUi
import com.example.newsapp.use_cases.FetchProvidersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProvidersViewModel @Inject constructor(
    private val fetchProvidersUseCase: FetchProvidersUseCase,
    private val workerDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProviderUiState())
    //todo do we need to use it hot flow ?
    val uiState = _uiState.asStateFlow()

    init {
        getProvidersList()
    }

    private fun getProvidersList() {
        fetchProvidersUseCase(null)
        fetchProvidersUseCase.observe().onEach { providersList ->
            updateUiState(
                _uiState.value.copy(
                    providersList = providersList,
                    isLoading = false
                )
            )
        }.launchIn(viewModelScope)

    }

    fun processActions(providersActions: ProvidersActions) {
        when (providersActions) {
            is ProvidersActions.ResetChanges -> resetUserChanges()
            is ProvidersActions.ClearFilter -> clearFilter()
            is ProvidersActions.SubmitFilter -> applyFilter()
            is ProvidersActions.ToggleProviderSelection
            -> toggleProviderSelectionState(providersActions.providerUi, providersActions.index)
        }
    }

    private fun getCurrentProvidersList() = _uiState.value.providersList

    private fun toggleProviderSelectionState(providerUi: ProviderUi, index: Int) {
        val newProviderList = getCurrentProvidersList().toMutableList()
        newProviderList[index] = providerUi.copy(isSelected = providerUi.isSelected.not()).apply {
            toggleUpdatesSaved()
        }
        updateProvidersList(newProviderList)
    }

    private fun applyFilter() {
        viewModelScope.launch(workerDispatcher) {
            saveProvidersSelectionsStates()
            updateUiState(_uiState.value.copy(
                selectedProvidersList = getSelectedProviders(),
                 shouldNavigateBack = true))

        }
    }

    private fun saveProvidersSelectionsStates(){
        _uiState.value.providersList.onEach {
            it.markAsSaved()
        }
    }

    private fun getSelectedProviders() = getCurrentProvidersList().filter { it.isSelected }

    private fun updateProvidersList(newProvidersList: List<ProviderUi>) {
        updateUiState(_uiState.value.copy(providersList = newProvidersList))
    }

    private fun updateUiState(providerUiState: ProviderUiState) {
        _uiState.tryEmit(providerUiState)
    }

    //todo fix that when relaunch the bottom sheet after
    // resetting changes the bottom sheet will open on the old state then it willl be updated
    private fun resetUserChanges() {
        viewModelScope.launch(workerDispatcher) {
            getCurrentProvidersList().onEach {
                if (!it.updatesSaved) {
                    it.toggleSelection()
                    it.toggleUpdatesSaved()
                }
            }
            updateUiState(_uiState.value.copy(
                providersList = getCurrentProvidersList().toList(),
                selectedProvidersList = getSelectedProviders(),
                shouldNavigateBack = false))
        }
    }

    private fun clearFilter(){
        getCurrentProvidersList().onEach {
            it.isSelected = false
            it.markAsSaved()
        }
        updateUiState(_uiState.value.copy(
            providersList = getCurrentProvidersList().toList(),
            selectedProvidersList = emptyList(),
            shouldNavigateBack = true))
    }

}