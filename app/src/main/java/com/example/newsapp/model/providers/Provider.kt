package com.example.newsapp.model.providers

import com.example.newsapp.model.FilterData

data class Provider(val id: String, val name: String, val isSelected: Boolean) {

    /** we override toString method to get list of strings of the providers list
     * @see[FilterData.convertProvidersToString]
     */
    override fun toString(): String {
        return id
    }

}
