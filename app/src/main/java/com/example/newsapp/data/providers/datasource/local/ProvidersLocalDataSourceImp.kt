package com.example.newsapp.data.providers.datasource.local

import com.example.newsapp.model.providers.ProviderEntity
import kotlinx.coroutines.flow.Flow

class ProvidersLocalDataSourceImp(private val providersDao: ProvidersDao)
    : ProvidersLocalDataSource {


    override  fun getProviders() = providersDao.getAllProvider()

    override fun getProviderCounts() = providersDao.getProviderCounts()

    override suspend fun insertAllProviders(sources: List<ProviderEntity>) {
        providersDao.insertAll(sources)
    }
}