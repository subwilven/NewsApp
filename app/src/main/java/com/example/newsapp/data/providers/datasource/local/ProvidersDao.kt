package com.example.newsapp.data.providers.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.model.providers.ProviderEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ProvidersDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sources: List<ProviderEntity>)

    @Query("SELECT * FROM provider")
    fun getAllProvider() : Flow<List<ProviderEntity>>


    @Query("SELECT COUNT(*) FROM provider")
    fun getProviderCounts(): Flow<Int>
}