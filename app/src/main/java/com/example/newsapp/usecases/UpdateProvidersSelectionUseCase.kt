package com.example.newsapp.usecases

import com.example.newsapp.data.providers.repository.ProvidersRepository
import com.example.newsapp.usecases.base.NoResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdateProvidersSelectionUseCase @Inject constructor(
    private val repository: ProvidersRepository,
    dispatcher: CoroutineDispatcher
) : NoResultUseCase<HashSet<String>>(dispatcher) {


    override suspend fun run(providersIds: HashSet<String>) {
        repository.updateSelectedProvidersList(providersIds)
    }
}
