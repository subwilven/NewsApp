package com.example.newsapp.use_cases

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.providers.ProviderUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchProvidersUseCase @Inject constructor(
    private val repository: ArticlesRepository
) {

    fun execute(): Flow<List<ProviderUi>> {
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