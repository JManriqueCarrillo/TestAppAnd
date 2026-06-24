package com.jmanrique.testappand.domain.usecases

import arrow.core.Either
import arrow.core.right
import com.jmanrique.testappand.core.Failure
import com.jmanrique.testappand.core.di.IoDispatcher
import com.jmanrique.testappand.core.entities.Product
import com.jmanrique.testappand.domain.BaseUseCase
import com.jmanrique.testappand.domain.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: ProductRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<Flow<List<Product>>>(dispatcher) {
    override suspend fun execute(): Either<Failure, Flow<List<Product>>> {
        return repository.getFavorites().right()
    }
}
