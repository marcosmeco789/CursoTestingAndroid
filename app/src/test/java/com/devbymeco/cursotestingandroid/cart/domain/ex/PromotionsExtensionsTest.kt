package com.devbymeco.cursotestingandroid.cart.domain.ex

import com.devbymeco.cursotestingandroid.core.builders.promotion
import com.devbymeco.cursotestingandroid.productlist.domain.model.Promotion
import org.junit.Assert.*
import org.junit.Test
import java.time.Instant

class PromotionsExtensionsTest {

    private val now = Instant.parse("2026-04-03T10:00:00Z")
    @Test
    fun given_future_promotion_when_active_at_then_exclude() {
        //Given
        val futurePromotion = promotion {
            withStartTime(now.plusSeconds(10))
            withEndTime(now.plusSeconds(100))
        }

        val promotions = listOf(futurePromotion)

        //When
        val resultado = promotions.activeAt(now)

        //Then

        assertEquals(0, resultado.size)

    }

    @Test
    fun given_expired_promotion_when_active_at_then_exclude(){
        //Given
        val expiredPromotion = promotion {
            withStartTime(now.minusSeconds(10))
            withEndTime(now.minusSeconds(1))
        }

        val promotions = listOf(expiredPromotion)

        // When
        val resultado = promotions.activeAt(now)

        // Then
        assertEquals(0, resultado.size)
    }

    @Test
    fun given_on_going_promotion_when_active_at_then_include(){
        //Given
        val onGoingPromotion = promotion {
            withStartTime(now.minusSeconds(10))
            withEndTime(now.plusSeconds(100))
        }

        val promotions = listOf(onGoingPromotion)

        // When
        val resultado = promotions.activeAt(now)

        // Then
        assertEquals(1, resultado.size)
    }

    @Test
    fun given_exact_start_time_promotion_when_active_at_then_include(){
        //Given
        val onGoingPromotion = promotion {
            withStartTime(now)
            withEndTime(now.plusSeconds(100))
        }

        val promotions = listOf(onGoingPromotion)

        // When
        val resultado = promotions.activeAt(now)

        // Then
        assertEquals(1, resultado.size)
    }

    @Test
    fun given_exact_end_time_promotion_when_active_at_then_include(){
        //Given
        val onGoingPromotion = promotion {
            withStartTime(now.minusSeconds(10))
            withEndTime(now)
        }

        val promotions = listOf(onGoingPromotion)

        // When
        val resultado = promotions.activeAt(now)

        // Then
        assertEquals(1, resultado.size)
    }


    @Test
    fun given_empty_list_when_active_at_then_return_empty_list(){
        //When
        val promotions = emptyList<Promotion>()

        //Then
        val resultado = promotions.activeAt(now)

        //When
        assertEquals(0, resultado.size)
    }

}