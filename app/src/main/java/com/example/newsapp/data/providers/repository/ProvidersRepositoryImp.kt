package com.example.newsapp.data.providers.repository

import android.util.Log
import com.example.newsapp.data.providers.datasource.local.ProvidersLocalDataSource
import com.example.newsapp.data.providers.datasource.remote.ProvidersRemoteDataSource
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.model.providers.ProviderEntity
import com.example.newsapp.model.providers.asUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class ProvidersRepositoryImp(
    private val localDataSource: ProvidersLocalDataSource,
    private val remoteDataSource: ProvidersRemoteDataSource
) : ProvidersRepository {

    private var selectedProvidersIds = MutableStateFlow<HashSet<String>>(hashSetOf())

    override fun getProviders(): Flow<List<ProviderEntity>> {
       return  flow {
                localDataSource.getProviders().collect {
                    delay(2500)//to show loading
                    if(it.isEmpty()) {
                        val fetchedSources = remoteDataSource.fetchProviders().providers
                        localDataSource.insertAllProviders(fetchedSources)
                    }else emit(it)
                }
        }
    }

    override fun getSelectedProvidersIds() = selectedProvidersIds
    override fun updateSelectedProvidersList(providersIds: HashSet<String>) {
        selectedProvidersIds.tryEmit(providersIds)
    }


}