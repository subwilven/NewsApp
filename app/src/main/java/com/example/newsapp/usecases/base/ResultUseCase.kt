package com.example.newsapp.usecases.base

import com.example.newsapp.di.Dispatcher
import com.example.newsapp.di.NewsDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class ResultUseCase<Type : Any, in Params>() {

    @Inject
    @Dispatcher(NewsDispatchers.IO)
    lateinit var workDispatcher: CoroutineDispatcher

    abstract suspend fun run(params: Params): Type

    suspend operator fun invoke(params: Params): Type = withContext(workDispatcher) {
        run(params)
    }
}
