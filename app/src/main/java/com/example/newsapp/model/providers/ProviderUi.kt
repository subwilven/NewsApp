package com.example.newsapp.model.providers

data class ProviderUi(val id: String, val name: String, var isSelected: Boolean) {

    /** this one used to mark that [isSelected] is changed or not so when
     * ser get back without applying the filter we restore the previous value*/
    var updatesSaved = true

    constructor(provider: Provider) : this(
        provider.id,
        provider.name,
        false
    )

    fun toggleUpdatesSaved(){
        updatesSaved = updatesSaved.not()
    }

    fun markAsSaved(){
        updatesSaved = true
    }

}