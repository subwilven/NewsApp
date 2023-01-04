package com.example.newsapp.model

import com.example.newsapp.model.providers.ProviderUi

data class FilterData(
    val searchInput: String?,
    val selectedProviders: List<ProviderUi>
)
