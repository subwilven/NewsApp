package com.example.newsapp.use_cases

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.providers.ProviderUi
import com.example.newsapp.use_cases.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchProvidersUseCase @Inject constructor(
    private val repository: ArticlesRepository,
    dispatcher: CoroutineDispatcher
) : FlowUseCase<List<ProviderUi>,Nothing?>(dispatcher) {

    override fun doWork(params: Nothing?): Flow<List<ProviderUi>> {
        //create this dummy instance to represent "all" option which means that all providers is selected
        val selectAllProviders = ProviderUi("all","All",true)
        return repository.getProviders().map {
            it.map { provider ->
                ProviderUi(provider)
            }.toMutableList().apply {
                add(0,selectAllProviders)
            }
        }
    }
}