package com.example.newsapp.usecases

import com.example.newsapp.data.providers.repository.ProvidersRepository
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.usecases.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class FetchProvidersUseCase @Inject constructor(
    private val repository: ProvidersRepository,
) : FlowUseCase<List<Provider>, Unit>() {

    override fun doWork(params: Unit): Flow<List<Provider>> {
        return combine(
            repository.getProviders(),
            repository.getSelectedProvidersIds()
        ) { providerEntities, selectedProvidersIds ->
            providerEntities.map {
                val isSelected = selectedProvidersIds.contains(it.id)
                it.toggleSelection(isSelected)
            }
        }
    }
}
