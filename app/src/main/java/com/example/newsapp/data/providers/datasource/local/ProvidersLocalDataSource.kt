package com.example.newsapp.data.providers.datasource.local

import com.example.newsapp.model.providers.ProviderEntity
import kotlinx.coroutines.flow.Flow

interface ProvidersLocalDataSource {

    fun getProviders() : Flow<List<ProviderEntity>>

    fun getProviderCounts() : Flow<Int>

    suspend fun insertAllProviders(sources : List<ProviderEntity>)
}
