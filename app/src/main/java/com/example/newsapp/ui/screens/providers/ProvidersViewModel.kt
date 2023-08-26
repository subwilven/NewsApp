package com.example.newsapp.ui.screens.providers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Result
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.usecases.FetchProvidersUseCase
import com.example.newsapp.usecases.UpdateProvidersSelectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProvidersViewModel @Inject constructor(
    private val fetchProvidersUseCase: FetchProvidersUseCase,
    private val updateProvidersSelectionUseCase: UpdateProvidersSelectionUseCase,
    private val workerDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProviderUiState(isLoading = true))
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
            val newProvider = provider.copy(isSelected = !provider.isSelected)
            getCurrentProvidersList().toMutableList().also { newList ->
                newList[index] = newProvider
                _uiState.value =  _uiState.value.copy(providersList = newList)
            }
        }
    }

    fun applyFilter() {
        viewModelScope.launch(workerDispatcher) {
            val selectedProvidersIds =
                getCurrentProvidersList().filter { it.isSelected }.map { it.id }.toHashSet()
            updateProvidersSelectionUseCase(selectedProvidersIds)
        }
    }


    fun resetFilter() {
        viewModelScope.launch {
            updateProvidersSelectionUseCase(hashSetOf())
        }
    }

}
