package com.devbymeco.cursotestingandroid.productlist.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.devbymeco.cursotestingandroid.productlist.data.local.database.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao{
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getProduct(id: String): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products:List<ProductEntity>)

    @Query("DELETE FROM products")
    suspend fun clearProducts()

    @Transaction
    suspend fun replaceAll(products: List<ProductEntity>){
        clearProducts()
        insertProducts(products)
    }
}