package com.example.newsapp.model.providers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "provider")
data  class ProviderEntity(
    @PrimaryKey()
    @ColumnInfo(name = "id")
    val id:String,
    @ColumnInfo(name = "name")
    val name:String)


fun ProviderEntity.asUiModel(isSelected : Boolean) = Provider(
    id = id,
    name = name,
    isSelected = isSelected,
)
