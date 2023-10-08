package com.example.newsapp.ui.screens.providers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Result
import com.example.newsapp.asResult
import com.example.newsapp.data.providers.repository.ProvidersRepository
import com.example.newsapp.di.Dispatcher
import com.example.newsapp.di.NewsDispatchers
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.usecases.FetchProvidersUseCase
import com.example.newsapp.usecases.UpdateProvidersSelectionUseCase
import com.example.newsapp.util.FLOW_SUBSCRIPTION_TIMEOUT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProvidersViewModel @Inject constructor(
    private val fetchProvidersUseCase: FetchProvidersUseCase,
    private val updateProvidersSelectionUseCase: UpdateProvidersSelectionUseCase,
    @Dispatcher(NewsDispatchers.Default) private val workerDispatcher: CoroutineDispatcher
) : ViewModel() {

    /**
     * This Flow acts as a temporary cache for storing the user's changes to the selected providers,
     * and is distinct from [ProvidersRepository.getSelectedProvidersIds] which holds the previous selected providers.
     * The changes stored in this Flow are only submitted to the data layer via [applyFilter] when the user clicks on the 'Apply' button.
     */
    private val selectedProvidersIdsFlow = MutableStateFlow<HashSet<String>>(hashSetOf())

    val uiState: StateFlow<ProviderUiState> = getProvidersList()
        .asResult()
        .map(::mapToUiState)
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(FLOW_SUBSCRIPTION_TIMEOUT),
            initialValue = ProviderUiState(isLoading = true)
        )

    private fun getProvidersList() = fetchProvidersUseCase(null)
        .onEach { providersList ->
            val selectedProvidersIds =
                providersList.filter { it.isSelected }.map { it.id }.toHashSet()
            selectedProvidersIdsFlow.emit(selectedProvidersIds)
        }.combine(selectedProvidersIdsFlow) { providersList, selectedProvidersIds ->
            providersList.map { provider ->
                provider.toggleSelection(provider.id in selectedProvidersIds)
            }
        }.flowOn(workerDispatcher)

    private fun mapToUiState(response: Result<List<Provider>>): ProviderUiState {
        return when (response) {
            is Result.Success -> ProviderUiState(providersList = response.data)
            is Result.Error -> ProviderUiState(errorMessage = response.exception?.message)
            is Result.Loading -> ProviderUiState(isLoading = true)
        }
    }

    fun toggleProviderSelectionState(provider: Provider) {
        viewModelScope.launch {
            val selectedProvidersSet = HashSet(selectedProvidersIdsFlow.value)
            if (selectedProvidersSet.contains(provider.id)) {
                selectedProvidersSet.remove(provider.id)
            } else {
                selectedProvidersSet.add(provider.id)
            }
            selectedProvidersIdsFlow.emit(selectedProvidersSet)
        }
    }

    fun applyFilter() {
        updateLocalSelectedProvidersIds(selectedProvidersIdsFlow.value)
    }

    fun resetFilter() {
        updateLocalSelectedProvidersIds(hashSetOf())
    }

    private fun updateLocalSelectedProvidersIds(providersIds: Set<String>) {
        viewModelScope.launch {
            updateProvidersSelectionUseCase(providersIds)
        }
    }

}
