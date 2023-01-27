package com.example.newsapp.use_cases

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.model.providers.ProviderEntity
import com.example.newsapp.model.providers.asUiModel
import com.example.newsapp.use_cases.base.ResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import javax.inject.Inject

class FetchProvidersUseCase @Inject constructor(
    private val repository: ArticlesRepository,
    dispatcher: CoroutineDispatcher
) : ResultUseCase<List<Provider>, Nothing?>(dispatcher) {

    override suspend fun run(params: Nothing?): List<Provider> {
        delay(2000)
        return repository.getProviders().map(ProviderEntity::asUiModel)
    }
}