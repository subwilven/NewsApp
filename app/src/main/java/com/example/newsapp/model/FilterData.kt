package com.example.newsapp.model

import android.text.TextUtils
import com.example.newsapp.util.Constants.Defaults.COUNTRY

data class FilterData(
    val searchInput: String?,
    val selectedProvidersIds: Set<String>,
) {

    fun convertProvidersToString(): String = TextUtils.join(",", selectedProvidersIds)

    fun getCountry() = if (selectedProvidersIds.isEmpty()) COUNTRY else null


    companion object {
        fun createDefault() = FilterData(null, hashSetOf())
    }
}
