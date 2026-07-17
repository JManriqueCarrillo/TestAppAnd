package com.jmanrique.testappand.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseUseCaseWithParams<in Params, out Type>(
    protected val coroutineDispatcher: CoroutineDispatcher
) {
    abstract suspend fun execute(params: Params): Type

    suspend operator fun invoke(params: Params): Type = withContext(coroutineDispatcher) {
        execute(params)
    }
}
