package com.devbymeco.cursotestingandroid.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devbymeco.cursotestingandroid.cart.data.local.database.dao.CartItemDao
import com.devbymeco.cursotestingandroid.cart.data.local.database.entity.CartItemEntity
import com.devbymeco.cursotestingandroid.productlist.data.local.database.dao.ProductDao
import com.devbymeco.cursotestingandroid.productlist.data.local.database.dao.PromotionDao
import com.devbymeco.cursotestingandroid.productlist.data.local.database.entity.ProductEntity
import com.devbymeco.cursotestingandroid.productlist.data.local.database.entity.PromotionEntity

@Database(
    entities = [ProductEntity::class, PromotionEntity::class, CartItemEntity::class],
    version = 1,
    exportSchema = true

)
abstract class MiniMarketDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun promotionDao(): PromotionDao
    abstract fun cartItemDao(): CartItemDao
}