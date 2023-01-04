package com.example.newsapp.use_cases

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.providers.ProviderUi
import com.example.newsapp.use_cases.base.FlowUseCase
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
) : FlowUseCase<List<ProviderUi>,Nothing?>(dispatcher) {

    override fun doWork(params: Nothing?): Flow<List<ProviderUi>> {
        return repository.getProviders().onStart {
            delay(3000) //just dummy loading to show loading
        }.map {
            it.map { provider ->
                ProviderUi(provider)
            }
        }
    }
}