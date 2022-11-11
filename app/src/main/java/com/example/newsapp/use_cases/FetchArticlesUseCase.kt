package com.example.newsapp.use_cases

import androidx.paging.PagingData
import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.articles.Article
import com.example.newsapp.util.StreamUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository
) : StreamUseCase<Unit, PagingData<Article>>() {

    override fun execute(parameters: Unit): Flow<PagingData<Article>>  {
        return repository.getArticlesStream("")
    }
}