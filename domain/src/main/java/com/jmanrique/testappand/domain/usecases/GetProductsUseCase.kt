package com.jmanrique.testappand.domain.usecases

import arrow.core.Either
import com.jmanrique.testappand.core.Failure
import com.jmanrique.testappand.core.di.IoDispatcher
import com.jmanrique.testappand.core.entities.Product
import com.jmanrique.testappand.domain.BaseUseCase
import com.jmanrique.testappand.domain.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<Either<Failure, List<Product>>>(dispatcher) {
    override suspend fun execute(): Either<Failure, List<Product>> {
        return repository.getProducts()
    }
}
