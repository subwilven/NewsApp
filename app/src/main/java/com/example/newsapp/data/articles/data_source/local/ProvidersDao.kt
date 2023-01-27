package com.example.newsapp.data.articles.data_source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.model.providers.ProviderEntity


@Dao
interface ProvidersDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sources: List<ProviderEntity>)

    @Query("SELECT * FROM provider")
    suspend fun getAllProvider() : List<ProviderEntity>


    @Query("SELECT COUNT(*) FROM provider")
    suspend fun getProviderCounts(): Int
}