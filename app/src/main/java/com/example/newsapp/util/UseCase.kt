package com.example.newsapp.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.example.newsapp.model.Result
import kotlinx.coroutines.Dispatchers


abstract class UseCase<in P, R>() {

    suspend operator fun invoke(parameters: P): R {
        return execute(parameters)
    }
//    suspend operator fun invoke(parameters: P): Result<R> {
//        return try {
//            withContext(Dispatchers.IO) {
//                execute(parameters).let {
//                    Result.Success(it)
//                }
//            }
//        } catch (e: Exception) {
//            Result.Error(e)
//        }
//    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}