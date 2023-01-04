package com.example.newsapp.ui.screens.providers

import com.example.newsapp.model.providers.ProviderUi

data class ProviderUiState (
    var providersList : List<ProviderUi> = emptyList(),
    var selectedProvidersList : List<ProviderUi> = emptyList(),
    var shouldNavigateBack :Boolean = false,
    var isLoading : Boolean = true,
    var errorMessage :String? = null
)