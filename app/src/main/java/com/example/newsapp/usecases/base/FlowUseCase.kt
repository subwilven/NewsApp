package com.example.newsapp.usecases.base

import com.example.newsapp.di.Dispatcher
import com.example.newsapp.di.NewsDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

abstract class FlowUseCase<Type : Any, Params : Any?> {

    @Inject
    @Dispatcher(NewsDispatchers.IO)
    lateinit var workDispatcher: CoroutineDispatcher

    operator fun invoke(params: Params) = doWork(params = params)
        .flowOn(workDispatcher)

    protected abstract fun doWork(params: Params): Flow<Type>

}
