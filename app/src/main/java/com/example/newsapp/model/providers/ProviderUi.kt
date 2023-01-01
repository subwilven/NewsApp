package com.example.newsapp.model.providers

data class ProviderUi(val id: String, val name: String, var isSelected: Boolean) {

    constructor(provider: Provider) : this(
        provider.id,
        provider.name,
        false
    )
}