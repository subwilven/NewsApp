package com.example.newsapp.ui.screens.providers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.providers.Provider
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

    private val _uiState = MutableStateFlow(ProviderUiState(isLoading = true))

    //todo do we need to use it hot flow ?
    val uiState = _uiState.asStateFlow()

    private val providersListFlow = MutableSharedFlow<List<Provider>>()
    private val onProviderSelectedFlow = MutableSharedFlow<Pair<Provider, Int>>()
    private val onFilterSubmitted = MutableStateFlow(false)

    init {

        onProviderSelectedFlow.onEach { (provider, index) ->
            toggleProviderSelectionState(provider, index)
        }.launchIn(viewModelScope)

        combine(
            providersListFlow.debounce(100),
            onFilterSubmitted,
        ) { providersList, isFilterSubmitted ->
            _uiState.value = ProviderUiState(
                providersList = providersList,
                onFiltrationProcessDone = isFilterSubmitted
            )

        }.launchIn(viewModelScope)

        fetchProvidersList()
    }


    private fun fetchProvidersList() {
        viewModelScope.launch {
            val fetchedProvidersList = fetchProvidersUseCase(null)
            providersListFlow.emit(fetchedProvidersList)
        }
    }

    fun processActions(providersActions: ProvidersActions) {
        when (providersActions) {
            is ProvidersActions.ResetChanges -> resetUserChanges()
            is ProvidersActions.ClearFilter -> clearFilter()
            is ProvidersActions.SubmitFilter -> applyFilter()
            is ProvidersActions.ToggleProviderSelection
            -> {
                emitToggleSelectionFlow(providersActions)
            }
        }
    }

    private fun getCurrentProvidersList() = _uiState.value.providersList

    private fun emitToggleSelectionFlow(action: ProvidersActions.ToggleProviderSelection) {
        viewModelScope.launch {
            onProviderSelectedFlow.emit(
                Pair(
                    action.provider,
                    action.index
                )
            )
        }
    }

    private fun toggleProviderSelectionState(provider: Provider, index: Int) {
        viewModelScope.launch(workerDispatcher) {
            val newProvider = provider.copy().apply {
                toggleSelection()
                toggleUpdatesSaved()
            }
            getCurrentProvidersList().toMutableList().also { newList ->
                newList[index] = newProvider
                providersListFlow.emit(newList)
            }
        }
    }

    private fun applyFilter() {
        viewModelScope.launch (workerDispatcher){
            saveProvidersSelectionsStates()
            onFilterSubmitted.emit(true)
        }
    }

    private fun saveProvidersSelectionsStates() {
        getCurrentProvidersList().onEach {
            it.markAsSaved()
        }
    }

    fun getSelectedProviders(): List<Provider> {
        onFilterSubmitted.update { false }
        return getCurrentProvidersList().filter { it.isSelected }
    }

    private fun resetUserChanges() {
        viewModelScope.launch(workerDispatcher) {
            getCurrentProvidersList().onEach {
                if (!it.updatesSaved) {
                    it.toggleSelection()
                    it.toggleUpdatesSaved()
                }
            }
            reEmitProvidersList()
        }
    }

    private suspend fun reEmitProvidersList() {
        providersListFlow.emit(getCurrentProvidersList().toList())
    }

    private fun clearFilter() {
        viewModelScope.launch(workerDispatcher) {
            getCurrentProvidersList().onEach {
                it.isSelected = false
                it.markAsSaved()
            }
            reEmitProvidersList()
            onFilterSubmitted.emit(true)
        }

    }

}