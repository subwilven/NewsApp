package com.example.newsapp.ui.screens.providers

import com.example.newsapp.model.providers.Provider

data class ProviderUiState (
    var providersList : List<Provider> = emptyList(),
    var onFiltrationProcessDone :Boolean = false,
    var isLoading : Boolean = false,
    var errorMessage :String? = null
)