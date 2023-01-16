package com.example.newsapp.use_cases.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
abstract class FlowUseCase<Type : Any, Params : Any?>(private val workDispatcher: CoroutineDispatcher) {

    private val mutableStateFlow = MutableSharedFlow<Params>(replay = 1)

    operator fun invoke(params: Params) = mutableStateFlow.tryEmit(params)

    protected abstract fun doWork(params: Params): Flow<Type>

    fun produce(params: Params): Flow<Type> = doWork(params = params)
        .flowOn(workDispatcher)

    fun observe(): Flow<Type> = mutableStateFlow
        .distinctUntilChanged()
        .flatMapLatest { doWork(it) }
        .flowOn(workDispatcher)
}