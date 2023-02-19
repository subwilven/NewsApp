package com.example.newsapp.use_cases.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class NoResultUseCase<in Params>(private val workDispatcher: CoroutineDispatcher) {

    abstract suspend fun run(params: Params)

    suspend operator fun invoke(params: Params) = withContext(workDispatcher) {
        run(params)
    }
}
