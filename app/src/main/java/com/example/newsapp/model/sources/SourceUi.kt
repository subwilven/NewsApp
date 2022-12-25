package com.example.newsapp.model.sources

data class SourceUi(val id: String, val name: String, var isSelected: Boolean) {

    constructor(source: Source) : this(
        source.id,
        source.name,
        false
    )
}