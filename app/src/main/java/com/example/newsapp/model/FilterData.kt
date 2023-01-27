package com.example.newsapp.model

import android.text.TextUtils
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.util.DEFAULT_COUNTRY

data class FilterData(
    val searchInput: String?,
    val selectedProviders: List<Provider>
){

    fun convertProvidersToString(): String = TextUtils.join(",", selectedProviders)

    fun getCountry() = if(selectedProviders.isEmpty()) DEFAULT_COUNTRY else null
}
