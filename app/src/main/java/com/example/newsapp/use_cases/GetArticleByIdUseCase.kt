package com.example.newsapp.use_cases

import androidx.paging.PagingData
import com.example.newsapp.data.articles.repository.ArticlesRepository
import com.example.newsapp.model.articles.Article
import com.example.newsapp.util.StreamUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticleByIdUseCase @Inject constructor(
    private val repository: ArticlesRepository
) : StreamUseCase<Int,Article>() {

    override fun execute(articleId: Int): Flow<Article>  {
        return repository.getArticleById(articleId)
    }
}