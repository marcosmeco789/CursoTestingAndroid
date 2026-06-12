package com.devbymeco.cursotestingandroid.detail.domain.usecase

import com.devbymeco.cursotestingandroid.core.builders.product
import com.devbymeco.cursotestingandroid.core.builders.promotion
import com.devbymeco.cursotestingandroid.core.domain.util.Clock
import com.devbymeco.cursotestingandroid.core.fakes.FakeProductRepository
import com.devbymeco.cursotestingandroid.core.fakes.FakePromotionRepository
import com.devbymeco.cursotestingandroid.core.fakes.FakeSystemClock
import com.devbymeco.cursotestingandroid.productlist.domain.repository.ProductRepository
import com.devbymeco.cursotestingandroid.productlist.domain.repository.PromotionRepository
import com.devbymeco.cursotestingandroid.productlist.domain.usecase.GetPromotionForProduct
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.Instant

class GetProductDetailWithPromotionUseCaseTest {

    private lateinit var clock: FakeSystemClock
    private lateinit var productRepository: FakeProductRepository
    private lateinit var promotionRepository: FakePromotionRepository

    @Before
    fun setup() {
        clock = FakeSystemClock().apply { setTime(Instant.parse("2026-04-03T10:00:00Z")) }
        productRepository = FakeProductRepository()
        promotionRepository = FakePromotionRepository()
    }

    private fun useCase() = GetProductDetailWithPromotionUseCase(
        productRepository,
        promotionRepository,
        GetPromotionForProduct(),
        clock
    )


    @Test
    fun given_active_promotion_when_invoke_then_returns_product_with_promotion() = runTest {
        // Given
        val productId = "p1"
        val p = product { withId(productId); withName("huevos") }
        val now = clock.now()

        val promo = promotion {
            withProductsIds(listOf(productId))
            withStartTime(now.minusSeconds(10))
            withEndTime(now.plusSeconds(10))
        }

        productRepository.setProducts(listOf(p))
        promotionRepository.setPromotions(listOf(promo))

        //When
        val result = useCase()(productId).first()

        //THEN
        assertNotNull(result)
        assertNotNull(result?.promotion)
        assertEquals(productId, result?.product?.id)

    }

    @Test
    fun given_expired_promotion_when_invoke_then_returns_product_without_promotion() = runTest {
        // Given
        val productId = "p1"
        val p = product { withId(productId); withName("huevos") }
        val now = clock.now()

        val promo = promotion {
            withProductsIds(listOf(productId))
            withStartTime(now.minusSeconds(10))
            withEndTime(now.minusSeconds(1))
        }

        productRepository.setProducts(listOf(p))
        promotionRepository.setPromotions(listOf(promo))

        //When
        val result = useCase()(productId).first()

        //Then
        assertNotNull(result?.product)
        assertNull(result?.promotion)

    }

    @Test
    fun given_non_existing_product_id_when_invokes_then_returns_null() = runTest {
        //Given
        productRepository.setProducts(emptyList())

        //When
        val result = useCase()("").first()

        //Then
        assertNull(result)
    }

    @Test
    fun given_active_promotion_when_time_advance_then_product_promotion_becomes_null() = runTest {
        // Given
        val productId = "p1"
        val p = product { withId(productId); withName("huevos") }
        val now = clock.now()

        val promo = promotion {
            withProductsIds(listOf(productId))
            withStartTime(now.minusSeconds(10))
            withEndTime(now.plusSeconds(5))
        }

        productRepository.setProducts(listOf(p))
        promotionRepository.setPromotions(listOf(promo))

        //When
        val result = useCase()(productId)

        //THEN
        assertNotNull(result.first()?.promotion)

        clock.advanceTime(6)

        assertNull(result.first()?.promotion)
    }
}