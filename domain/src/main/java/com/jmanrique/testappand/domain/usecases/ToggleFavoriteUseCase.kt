package com.jmanrique.testappand.domain.usecases

import arrow.core.Either
import com.jmanrique.testappand.core.Failure
import com.jmanrique.testappand.core.di.IoDispatcher
import com.jmanrique.testappand.core.entities.Product
import com.jmanrique.testappand.domain.BaseUseCaseWithParams
import com.jmanrique.testappand.domain.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: ProductRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCaseWithParams<Product, Either<Failure, Unit>>(dispatcher) {
    override suspend fun execute(params: Product): Either<Failure, Unit> {
        return if (params.isFavorite) {
            repository.removeFavorite(params)
        } else {
            repository.addFavorite(params)
        }
    }
}
