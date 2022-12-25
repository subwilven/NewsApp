package com.example.newsapp.model.sources

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "source")
data  class Source(
    @PrimaryKey()
    @ColumnInfo(name = "id")
    val id:String,
    @ColumnInfo(name = "name")
    val name:String)