package com.example.newsapp.data.feed.repository

import com.example.newsapp.model.Feed
import javax.inject.Inject

class FeedsRepository @Inject constructor() {
    fun fetchFeeds() :List<Feed>{
        return listOf(Feed("Test"))
    }
}