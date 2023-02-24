package com.example.newsapp.model

import android.text.TextUtils
import com.example.newsapp.util.DEFAULT_COUNTRY

data class FilterData(
    val searchInput: String?,
    val selectedProvidersIds: HashSet<String>,
){

    fun convertProvidersToString(): String = TextUtils.join(",", selectedProvidersIds)

    fun getCountry() = if(selectedProvidersIds.isEmpty()) DEFAULT_COUNTRY else null
}
