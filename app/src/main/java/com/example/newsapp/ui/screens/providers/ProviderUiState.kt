package com.example.newsapp.ui.screens.providers

import com.example.newsapp.model.providers.Provider

data class ProviderUiState (
    var providersList : List<Provider> = emptyList(),
    var isLoading : Boolean = false,
    var errorMessage :String? = null
)
