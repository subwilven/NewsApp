package com.example.newsapp.data.providers.repository

import com.example.newsapp.model.providers.Provider
import com.example.newsapp.model.providers.ProviderEntity
import kotlinx.coroutines.flow.Flow

interface ProvidersRepository {
    fun  getProviders() : Flow<List<ProviderEntity>>
    fun  getSelectedProvidersIds() : Flow<HashSet<String>>
    fun updateSelectedProvidersList(providersIds : HashSet<String>)
}