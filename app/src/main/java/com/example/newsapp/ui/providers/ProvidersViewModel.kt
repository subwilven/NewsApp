package com.example.newsapp.ui.providers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.providers.ProviderUi
import com.example.newsapp.use_cases.FetchProvidersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProvidersViewModel @Inject constructor(
    private val fetchProvidersUseCase: FetchProvidersUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProviderUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getProvidersList()
    }

    private fun getProvidersList(){
        fetchProvidersUseCase(null)
        fetchProvidersUseCase.observe().onEach { providersList ->
            delay(3000) //just dummy loading to show loading
            updateUiState(
                _uiState.value.copy(
                    providersList = providersList,
                    isLoading = false
                )
            )
        }.launchIn(viewModelScope)

    }

    fun onProviderSelected(providerUi: ProviderUi, index: Int) {
        val newProviderList = _uiState.value.providersList.toMutableList()
        newProviderList[index] = providerUi.copy(isSelected = providerUi.isSelected.not())
        updateUiState(_uiState.value.copy(providersList = newProviderList))
    }

    private fun updateUiState(providerUiState: ProviderUiState) {
        _uiState.tryEmit(providerUiState)
    }

}