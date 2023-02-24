package com.example.newsapp.usecases

import com.example.newsapp.Result
import com.example.newsapp.asResult
import com.example.newsapp.data.providers.repository.ProvidersRepository
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.model.providers.asUiModel
import com.example.newsapp.usecases.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class FetchProvidersUseCase @Inject constructor(
    private val repository: ProvidersRepository,
    dispatcher: CoroutineDispatcher
) : FlowUseCase<Result<List<Provider>>, Nothing?>(dispatcher) {

    override fun doWork(params: Nothing?): Flow<Result<List<Provider>>> {
        return combine(
            repository.getProviders(),
            repository.getSelectedProvidersIds()
        ) { providerEntities, selectedProvidersIds ->
            providerEntities.map {
                val isSelected = selectedProvidersIds.contains(it.id)
                it.asUiModel(isSelected)
            }
        }.asResult()
    }
}
