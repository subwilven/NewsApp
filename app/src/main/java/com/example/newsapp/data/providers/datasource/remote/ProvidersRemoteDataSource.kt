package com.example.newsapp.data.providers.datasource.remote

import com.example.newsapp.model.providers.ProviderResponse

interface ProvidersRemoteDataSource {
    suspend fun fetchProviders() : ProviderResponse
}
