package com.example.newsapp.data.providers.repository

import com.example.newsapp.data.providers.datasource.local.ProvidersLocalDataSource
import com.example.newsapp.data.providers.datasource.remote.ProvidersRemoteDataSource
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.model.providers.ProviderNetwork
import com.example.newsapp.model.providers.asEntityModel
import com.example.newsapp.model.providers.asUiModel
import com.example.newsapp.util.Constants.Defaults.DELAY_DUMMY_LOADING
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class ProvidersRepositoryImp(
    private val localDataSource: ProvidersLocalDataSource,
    private val remoteDataSource: ProvidersRemoteDataSource
) : ProvidersRepository {

    private var selectedProvidersIds = MutableStateFlow<Set<String>>(hashSetOf())

    override fun getProviders(): Flow<List<Provider>> {
        return flow {
            localDataSource.getProviders().collect { localProvidersList ->
                if (localProvidersList.isEmpty()) {
                    fetchProviders()
                } else {
                    emit(localProvidersList.map { it.asUiModel() })
                }
            }
        }
    }

    private suspend fun fetchProviders() {
        delay(DELAY_DUMMY_LOADING)//to show more loading time
        val fetchedSources = remoteDataSource.fetchProviders().providers
        localDataSource.insertAllProviders(fetchedSources.map(ProviderNetwork::asEntityModel))
    }

    override fun getSelectedProvidersIds() = selectedProvidersIds

    override suspend fun updateSelectedProvidersList(providersIds: Set<String>) {
        selectedProvidersIds.emit(providersIds)
    }
}
