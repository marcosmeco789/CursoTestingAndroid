package com.devbymeco.cursotestingandroid.cart.domain.usecase

import com.devbymeco.cursotestingandroid.core.builders.cartItem
import com.devbymeco.cursotestingandroid.core.builders.product
import com.devbymeco.cursotestingandroid.core.domain.model.AppError
import com.devbymeco.cursotestingandroid.core.fakes.FakeCartItemRepository
import com.devbymeco.cursotestingandroid.core.fakes.FakeProductRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class UpdateCartItemUseCaseTest {
    @Test
    fun given_quantity_lower_than_0_when_invoke_then_quantity_must_be_positive_exception() =
        runTest {
            //Given
            val fakeCartItemRepository = FakeCartItemRepository()
            val fakeProductRepository = FakeProductRepository()

            val productId = "product-1"
            val quantity = -2

            val useCase = UpdateCartItemUseCase(fakeCartItemRepository, fakeProductRepository)

            //When
            val respuesta = runCatching { useCase(productId, quantity) }.exceptionOrNull()

            //Then
            assertTrue(respuesta is AppError.Validation.QuantityMustBePositive)
        }


    @Test
    fun given_quantity_equals_0_when_invoke_then_remove_items_from_cart() = runTest {
        //Given
        val productId = "id1"

        val product = product {
            withId(productId)
        }

        val cartItemProduct = cartItem {
            withProductId(productId)
            withQuantity(3)
        }

        val fakeProductRepository = FakeProductRepository().apply { setProducts(listOf(product)) }
        val fakeCartItemRepository = FakeCartItemRepository().apply {
            setCartItems(listOf(cartItemProduct))
        }


        val useCase = UpdateCartItemUseCase(fakeCartItemRepository, fakeProductRepository)

        //When
        val resultado = useCase(productId, 0)

        //Then
        val items = fakeCartItemRepository.getCartItems().first()
        assertEquals(0, items.size)
    }


    @Test
    fun given_missing_product_when_invoke_then_throws_not_found() = runTest {
        // Given
        val productId = "id1"

        val fakeProductRepository = FakeProductRepository().apply { setProducts(emptyList()) }
        val fakeCartItemRepository = FakeCartItemRepository()

        val useCase = UpdateCartItemUseCase(fakeCartItemRepository, fakeProductRepository)
        // When
        val resultado = runCatching {   useCase(productId, 1)}.exceptionOrNull()

        // Then
        assertTrue(resultado is AppError.NotFoundError)
    }

    @Test
    fun given_higher_quantity_than_stock_when_invoke_then_throws_insufficient_stock() = runTest {
        // Given
        val productId = "product-id"

        val product = product {
            withId(productId)
            withStock(3)
        }

        val cartItem = cartItem {
            withProductId("product-id")
            withQuantity(1)
        }

        val fakeCartItemRepository = FakeCartItemRepository().apply { setCartItems(listOf(cartItem)) }
        val fakeProductRepository = FakeProductRepository().apply { setProducts(listOf(product)) }

        val useCase = UpdateCartItemUseCase(fakeCartItemRepository, fakeProductRepository)

        //When
        val response = runCatching { useCase(productId, 5) }.exceptionOrNull()

        //Then
        assertTrue(response is AppError.Validation.InsufficientStock)
    }

    @Test
    fun given_valid_quantity_and_product_when_invoke_then_updates_cart_item() = runTest{
        // Given
        val productId = "product-id"

        val product = product {
            withId(productId)
            withStock(20)
        }

        val cartItem = cartItem {
            withProductId("product-id")
            withQuantity(1)
        }

        val fakeCartItemRepository = FakeCartItemRepository().apply { setCartItems(listOf(cartItem)) }
        val fakeProductRepository = FakeProductRepository().apply { setProducts(listOf(product)) }

        val useCase = UpdateCartItemUseCase(fakeCartItemRepository, fakeProductRepository)

        //When

        val resultado = useCase(productId, 5)

        //Then
        val items = fakeCartItemRepository.getCartItems().first()
        assertEquals(1, items.size)
        assertEquals(5, items.first().quantity)
    }
}