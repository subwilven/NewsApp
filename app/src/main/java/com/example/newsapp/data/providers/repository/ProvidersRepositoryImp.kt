package com.example.newsapp.data.providers.repository

import com.example.newsapp.data.providers.datasource.local.ProvidersLocalDataSource
import com.example.newsapp.data.providers.datasource.remote.ProvidersRemoteDataSource
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.model.providers.ProviderNetwork
import com.example.newsapp.model.providers.asEntityModel
import com.example.newsapp.model.providers.asUiModel
import com.example.newsapp.util.DELAY_DUMMY_LOADING
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class ProvidersRepositoryImp(
    private val localDataSource: ProvidersLocalDataSource,
    private val remoteDataSource: ProvidersRemoteDataSource
) : ProvidersRepository {

    private var selectedProvidersIds = MutableStateFlow<HashSet<String>>(hashSetOf())

    override fun getProviders(): Flow<List<Provider>> {
        return flow {
            localDataSource.getProviders().collect { it ->
                if (it.isEmpty()) {
                    delay(DELAY_DUMMY_LOADING)//to show loading
                    val fetchedSources = remoteDataSource.fetchProviders().providers
                    localDataSource.insertAllProviders(fetchedSources.map(ProviderNetwork::asEntityModel))
                } else emit(it.map { it.asUiModel() })
            }
        }
    }

    override fun getSelectedProvidersIds() = selectedProvidersIds

    override suspend fun updateSelectedProvidersList(providersIds: HashSet<String>) {
        selectedProvidersIds.emit(providersIds)
    }
}
