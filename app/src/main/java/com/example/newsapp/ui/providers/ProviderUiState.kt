package com.example.newsapp.ui.providers

import com.example.newsapp.model.providers.ProviderUi

data class ProviderUiState (
    var providersList : List<ProviderUi> = emptyList(),
    var isLoading : Boolean = true,
    var errorMessage :String? = null
)