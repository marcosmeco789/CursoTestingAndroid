package com.devbymeco.cursotestingandroid.productlist.domain.repository

import com.devbymeco.cursotestingandroid.productlist.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    fun getProductById(id: String) : Flow<Product?>

    suspend fun refreshProduct()
}