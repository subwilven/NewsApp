package com.example.newsapp.data.articles.data_source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.model.articles.Article
import com.example.newsapp.model.sources.Source
import kotlinx.coroutines.flow.Flow

@Dao
interface SourcesDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sources: List<Source>)

    @Query("SELECT * FROM source")
    fun getAllSources() : Flow<List<Source>>

}