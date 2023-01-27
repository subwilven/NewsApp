package com.example.newsapp.ui.screens.providers

import com.example.newsapp.model.providers.Provider

sealed class ProvidersActions {
    object SubmitFilter : ProvidersActions()
    data class ToggleProviderSelection(val provider: Provider, val index: Int) :
        ProvidersActions()

    object ResetChanges : ProvidersActions()
    object ClearFilter : ProvidersActions()
}