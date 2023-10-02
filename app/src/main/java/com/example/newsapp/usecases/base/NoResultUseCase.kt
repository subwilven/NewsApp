package com.example.newsapp.usecases.base

import com.example.newsapp.di.Dispatcher
import com.example.newsapp.di.NewsDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class NoResultUseCase<in Params> {

    @Inject
    @Dispatcher(NewsDispatchers.IO)
    lateinit var workDispatcher: CoroutineDispatcher

    abstract suspend fun run(params: Params)

    suspend operator fun invoke(params: Params) = withContext(workDispatcher) {
        run(params)
    }
}
