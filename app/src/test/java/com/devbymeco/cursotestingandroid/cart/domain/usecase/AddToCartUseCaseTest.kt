package com.devbymeco.cursotestingandroid.cart.domain.usecase

import com.devbymeco.cursotestingandroid.core.builders.product
import com.devbymeco.cursotestingandroid.core.domain.model.AppError
import com.devbymeco.cursotestingandroid.core.fakes.FakeCartItemRepository
import com.devbymeco.cursotestingandroid.core.fakes.FakeProductRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import javax.annotation.meta.When

class AddToCartUseCaseTest {

    @Test
    fun zero_quantity_throws_QuantityMustBePositive() = runTest {
        // Given
        val fakeCartRepository = FakeCartItemRepository()
        val fakeProductRepository = FakeProductRepository()
        val useCase = AddToCartUseCase(fakeCartRepository, fakeProductRepository)

        //When
        val exception = runCatching { useCase("id", 0) }.exceptionOrNull()

        //Then
        assertTrue(exception is AppError.Validation.QuantityMustBePositive)

    }

    @Test
    fun negative_quantity_throws_QuantityMustBePositive() = runTest {
        // Given
        val fakeCartRepository = FakeCartItemRepository()
        val fakeProductRepository = FakeProductRepository()
        val useCase = AddToCartUseCase(fakeCartRepository, fakeProductRepository)

        //When
        val exception = runCatching { useCase("id", -2) }.exceptionOrNull()

        //Then
        assertTrue(exception is AppError.Validation.QuantityMustBePositive)

    }


    @Test
    fun non_existing_product_throws_NotFoundError() = runTest {
        // Given
        val fakeCartRepository = FakeCartItemRepository()
        val fakeProductRepository = FakeProductRepository().apply {
            setProducts(emptyList())
        }
        val useCase = AddToCartUseCase(fakeCartRepository, fakeProductRepository)

        //When
        val exception = runCatching { useCase("id", 1) }.exceptionOrNull()

        //Then
        assertTrue(exception is AppError.NotFoundError)

    }

    @Test
    fun insufficient_stock_throws_InsufficientStock() = runTest {
        // Given
        val productId = "id-test-1"
        val product = product {
            withId(productId)
            withStock(2)
        }
        val fakeCartRepository = FakeCartItemRepository()
        val fakeProductRepository = FakeProductRepository().apply {
            setProducts(listOf(product))
        }

        val useCase = AddToCartUseCase(fakeCartRepository, fakeProductRepository)

        // When
        val exception = runCatching {
            useCase(productId, quantity = 5)
        }.exceptionOrNull()

        // Then
        assertTrue(exception is AppError.Validation.InsufficientStock)
        assertEquals(2 ,(exception as AppError.Validation.InsufficientStock).available)
    }

    @Test
    fun successful_case_adds_item_to_cart() = runTest {
        // Given
        val productId = "id-test-1"
        val product = product {
            withId(productId)
            withStock(10)
        }
        val fakeCartRepository = FakeCartItemRepository()
        val fakeProductRepository = FakeProductRepository().apply {
            setProducts(listOf(product))
        }

        val useCase = AddToCartUseCase(fakeCartRepository, fakeProductRepository)

        // When
        useCase(productId, 3)

        //Then
        val items = fakeCartRepository.getCartItems().first()
        assertEquals(productId, items.first().productId)
        assertEquals(1, items.size)
        assertEquals(3, items.first().quantity)
    }

    @Test
    fun default_quantity_adds_one_item()= runTest {
        // Given
        val productId = "id-test-1"
        val product = product {
            withId(productId)
            withStock(10)
        }
        val fakeCartRepository = FakeCartItemRepository()
        val fakeProductRepository = FakeProductRepository().apply {
            setProducts(listOf(product))
        }

        val useCase = AddToCartUseCase(fakeCartRepository, fakeProductRepository)

        //When
        useCase(productId)

        //then
        val items = fakeCartRepository.getCartItems().first()
        assertEquals(1, items.size)
        assertEquals(1, items.first().quantity)

    }
}