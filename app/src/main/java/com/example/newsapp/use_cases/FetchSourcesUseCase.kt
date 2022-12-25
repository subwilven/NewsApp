package com.example.newsapp.use_cases

import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.sources.Source
import com.example.newsapp.model.sources.SourceUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchSourcesUseCase @Inject constructor(
    private val repository: ArticlesRepository
) {

    suspend fun execute(): Flow<List<SourceUi>> {
        return repository.getSources().map { it.map { SourceUi(it) } }
    }
}