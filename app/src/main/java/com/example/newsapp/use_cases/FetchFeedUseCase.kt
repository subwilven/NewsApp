package com.example.newsapp.use_cases

import com.example.newsapp.data.feed.repository.FeedsRepository
import com.example.newsapp.model.Feed
import com.example.newsapp.util.UseCase
import javax.inject.Inject

class FetchFeedUseCase @Inject constructor(
    private val repository: FeedsRepository
) : UseCase<Unit, List<Feed>>() {

    override suspend fun execute(parameters: Unit): List<Feed> {
        return repository.fetchFeeds()
    }
}