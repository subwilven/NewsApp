package com.example.newsapp.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.data.feed.data_source.local.FeedsDao

abstract class AppDatabase  : RoomDatabase() {

    abstract fun feedsDao(): FeedsDao

    companion object {
        private const val databaseName = "newsApp-db"

        fun buildDatabase(context: Context): AppDatabase {
            //todo handle mirgrations
            return Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}