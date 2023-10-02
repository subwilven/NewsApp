package com.example.newsapp.data.providers.repository

import com.example.newsapp.model.providers.Provider
import kotlinx.coroutines.flow.Flow

interface ProvidersRepository {
    fun getProviders(): Flow<List<Provider>>
    fun getSelectedProvidersIds(): Flow<Set<String>>
    suspend fun updateSelectedProvidersList(providersIds: Set<String>)
}
