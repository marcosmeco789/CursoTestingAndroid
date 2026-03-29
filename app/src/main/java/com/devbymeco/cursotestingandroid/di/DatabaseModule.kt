package com.devbymeco.cursotestingandroid.di

import android.content.Context
import androidx.room.Room
import com.devbymeco.cursotestingandroid.productlist.data.local.database.MiniMarketDatabase
import com.devbymeco.cursotestingandroid.productlist.data.local.database.dao.ProductDao
import com.devbymeco.cursotestingandroid.productlist.data.local.database.dao.PromotionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMiniMarketDatabase(
        @ApplicationContext context: Context
    ): MiniMarketDatabase {
        return Room.databaseBuilder(
            context,
            MiniMarketDatabase::class.java,
            "minimarket_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: MiniMarketDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun providePromotionDao(database: MiniMarketDatabase): PromotionDao {
        return database.promotionDao()
    }
}

