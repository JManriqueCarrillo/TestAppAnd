package com.jmanrique.testappand.domain

import arrow.core.Either
import com.jmanrique.testappand.core.Failure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseUseCase<out Type>(
    private val coroutineDispatcher: CoroutineDispatcher
) where Type : Any? {
    abstract suspend fun execute(): Either<Failure, Type>

    suspend operator fun invoke(
        onResult: suspend (Type) -> Unit = {},
        onError: suspend (error: Failure) -> Unit = {}
    ) {
        withContext(coroutineDispatcher) {
            execute().fold(
                ifLeft = { error ->
                    onError(error)
                },
                ifRight = {
                    onResult(it)
                }
            )
        }
    }
}
