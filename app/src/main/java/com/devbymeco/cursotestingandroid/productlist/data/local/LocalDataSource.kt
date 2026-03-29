package com.devbymeco.cursotestingandroid.productlist.data.local

import com.devbymeco.cursotestingandroid.productlist.data.local.database.dao.ProductDao
import com.devbymeco.cursotestingandroid.productlist.data.local.database.dao.PromotionDao
import com.devbymeco.cursotestingandroid.productlist.data.local.database.entity.ProductEntity
import com.devbymeco.cursotestingandroid.productlist.domain.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val productDao: ProductDao,
    private val promotionDao: PromotionDao
) {

    fun getAllProducts(): Flow<List<ProductEntity>> = productDao.getAllProducts()

    suspend fun saveProducts(products: List<ProductEntity>){
        productDao.replaceAll(products)
    }
}