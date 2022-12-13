package com.example.newsapp.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.example.newsapp.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow


abstract class StreamUseCase<R>() {

    abstract operator fun invoke(vararg arg :Any) :R


}