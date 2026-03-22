package com.devbymeco.cursotestingandroid.di

import com.devbymeco.cursotestingandroid.core.data.coroutines.DefaultDispatchersProvider
import com.devbymeco.cursotestingandroid.core.domain.coroutines.DispatchersProvider
import com.devbymeco.cursotestingandroid.productlist.data.repository.ProductRepositoryImpl
import com.devbymeco.cursotestingandroid.productlist.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDispatchersProvider(defaultDispatchersProvider: DefaultDispatchersProvider): DispatchersProvider{
        return defaultDispatchersProvider
    }

    @Provides
    @Singleton
    fun provideProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository {
        return productRepositoryImpl
    }
}