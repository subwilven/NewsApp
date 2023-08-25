package com.example.newsapp.model.providers

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data  class ProviderNetwork(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id:String?,
    @ColumnInfo(name = "name")
    val name:String?
    )


fun ProviderNetwork.asEntityModel() = ProviderEntity(
    id = id.toString(),
    name = name.toString(),
)
