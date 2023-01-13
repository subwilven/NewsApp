package com.example.newsapp.use_cases

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.providers.ProviderUi
import com.example.newsapp.use_cases.base.FlowUseCase
import com.example.newsapp.use_cases.base.ResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FetchProvidersUseCase @Inject constructor(
    private val repository: ArticlesRepository,
    dispatcher: CoroutineDispatcher
) : ResultUseCase<List<ProviderUi>, Nothing?>(dispatcher) {

    override suspend fun run(params: Nothing?): List<ProviderUi> {
        delay(2000)
        return repository.getProviders().map { provider ->
               ProviderUi(provider)
       }
    }
}