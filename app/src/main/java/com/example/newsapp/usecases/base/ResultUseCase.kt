package com.example.newsapp.usecases.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class ResultUseCase<Type : Any, in Params> (private val workDispatcher: CoroutineDispatcher) {

    abstract suspend fun run(params: Params): Type

    suspend operator fun invoke(params: Params): Type = withContext(workDispatcher) {
        run(params)
    }
}
