package com.example.newsapp.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.example.newsapp.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow


abstract class StreamUseCase<in P, R>() {

    operator fun invoke(parameters: P): Flow<R> {
        return execute(parameters)
    }

    @Throws(RuntimeException::class)
    protected abstract  fun execute(parameters: P): Flow<R>

}