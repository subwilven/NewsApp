package com.example.newsapp.use_cases

import androidx.paging.PagingData
import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.articles.Article
import com.example.newsapp.util.StreamUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(
    private val repository: ArticlesRepository
) : StreamUseCase<String?, PagingData<Article>>() {

    override fun execute(query: String?): Flow<PagingData<Article>>  {
        return repository.getArticlesStream(query)
    }
}