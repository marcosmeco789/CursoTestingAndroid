package com.devbymeco.cursotestingandroid.productlist.domain.usecase

import com.devbymeco.cursotestingandroid.productlist.domain.model.Product
import com.devbymeco.cursotestingandroid.productlist.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productsRepository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>>{
        return productsRepository.getProducts()
    }
}