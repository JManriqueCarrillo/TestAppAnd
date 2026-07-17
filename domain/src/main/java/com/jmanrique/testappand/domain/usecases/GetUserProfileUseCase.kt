package com.jmanrique.testappand.domain.usecases

import arrow.core.Either
import com.jmanrique.testappand.core.Failure
import com.jmanrique.testappand.core.di.IoDispatcher
import com.jmanrique.testappand.domain.BaseUseCaseWithParams
import com.jmanrique.testappand.domain.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: ProductRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCaseWithParams<Int, Either<Failure, String>>(dispatcher) {
    override suspend fun execute(params: Int): Either<Failure, String> =
        repository.getUserProfile(params)

}
