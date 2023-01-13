package com.example.newsapp.ui.screens.providers

import com.example.newsapp.model.providers.ProviderUi

data class ProviderUiState (
    var providersList : List<ProviderUi> = emptyList(),
    var onFiltrationProcessDone :Boolean = false,
    var isLoading : Boolean = false,
    var errorMessage :String? = null
)