package com.example.newsapp.usecases

import com.example.newsapp.data.providers.repository.ProvidersRepository
import com.example.newsapp.usecases.base.NoResultUseCase
import javax.inject.Inject

class UpdateProvidersSelectionUseCase @Inject constructor(
    private val repository: ProvidersRepository,
) : NoResultUseCase<Set<String>>() {

    override suspend fun run(providersIds: Set<String>) {
        repository.updateSelectedProvidersList(providersIds)
    }
}
