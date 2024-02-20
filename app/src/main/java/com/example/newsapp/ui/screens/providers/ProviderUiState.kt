package com.example.newsapp.ui.screens.providers

import com.example.newsapp.model.providers.Provider

data class ProviderUiState (
    val providersList: List<Provider> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
