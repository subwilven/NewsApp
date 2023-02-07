package com.example.newsapp.ui.screens.providers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Result
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.use_cases.FetchProvidersUseCase
import com.example.newsapp.use_cases.UpdateProvidersSelectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProvidersViewModel @Inject constructor(
    private val fetchProvidersUseCase: FetchProvidersUseCase,
    private val updateProvidersSelectionUseCase: UpdateProvidersSelectionUseCase,
    private val workerDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProviderUiState(isLoading = true))

    //todo do we need to use it hot flow ?
    val uiState = _uiState.asStateFlow()

    init {
        fetchProvidersList()
    }

   private fun fetchProvidersList() {
        viewModelScope.launch {
            fetchProvidersUseCase(null)
            fetchProvidersUseCase.observe().onEach { providersListResult ->
                _uiState.value = when (providersListResult) {
                    is Result.Success -> {
                        ProviderUiState(providersList = providersListResult.data)
                    }
                    is Result.Error -> {
                        ProviderUiState(errorMessage = providersListResult.exception?.message)
                    }
                    is Result.Loading -> {
                        ProviderUiState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getCurrentProvidersList() = _uiState.value.providersList

    fun toggleProviderSelectionState(provider: Provider, index: Int) {
        viewModelScope.launch(workerDispatcher) {
            val newProvider = provider.copy().apply {
                toggleSelection()
                toggleUpdatesSaved()
            }
            getCurrentProvidersList().toMutableList().also { newList ->
                newList[index] = newProvider
                _uiState.value =  _uiState.value.copy(providersList = newList)
            }
        }
    }

    fun applyFilter() {
        viewModelScope.launch(workerDispatcher) {
            val selectedProvidersIds = getCurrentProvidersList().run {
                filter { it.isSelected }.map { it.markAsSaved() ; it.id}.toHashSet()
            }
            updateProvidersSelectionUseCase(selectedProvidersIds)
        }

    }

    /**
     * call this func while screen is closing so any user selections without applying the filter
     * should be reset to the previous state ,
     * so when user open the screen again it shows only the applied filter
     * */
    fun resetUserChanges() {
        viewModelScope.launch(workerDispatcher) {
            getCurrentProvidersList().onEach {
                if (!it.updatesSaved) {
                    it.toggleSelection()
                    it.toggleUpdatesSaved()
                }
            }
        }
    }

    fun resetFilter() {
        viewModelScope.launch(workerDispatcher) {
            getCurrentProvidersList().onEach {
                it.isSelected = false
            }
            applyFilter()
        }

    }

}