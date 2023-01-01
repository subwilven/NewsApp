package com.example.newsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.data.articles.data_source.local.ArticlesDao
import com.example.newsapp.data.articles.data_source.local.ProvidersDao
import com.example.newsapp.model.articles.Article
import com.example.newsapp.model.providers.Provider
import com.example.newsapp.util.DateConverterRoom

@Database(
    entities = [Article::class, Provider::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverterRoom::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao
    abstract fun providersDao(): ProvidersDao

    companion object {
        private const val databaseName = "newsApp-db"

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                .fallbackToDestructiveMigration()
                .build()
    }
}