package com.jmanrique.testappand.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseUseCase<out Type>(
    protected val coroutineDispatcher: CoroutineDispatcher
) {
    abstract suspend fun execute(): Type

    suspend operator fun invoke(): Type = withContext(coroutineDispatcher) {
        execute()
    }
}
