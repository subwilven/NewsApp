package com.example.newsapp.ui.screens.providers

import com.example.newsapp.model.providers.ProviderUi

sealed class ProvidersActions {
    object SubmitFilter : ProvidersActions()
    data class ToggleProviderSelection(val providerUi: ProviderUi, val index: Int) :
        ProvidersActions()

    object ResetChanges : ProvidersActions()
    object ClearFilter : ProvidersActions()
}