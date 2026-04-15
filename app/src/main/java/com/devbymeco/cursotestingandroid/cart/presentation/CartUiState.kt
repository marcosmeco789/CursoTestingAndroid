package com.devbymeco.cursotestingandroid.cart.presentation

import com.devbymeco.cursotestingandroid.cart.domain.model.CartSummary
import com.devbymeco.cursotestingandroid.cart.presentation.model.CartItemWithPromotion

sealed class CartUiState {
    data class Success(
        val summary: CartSummary? = null,
        val cartItems: List<CartItemWithPromotion>,
        val isLoading: Boolean
    ) : CartUiState()

    data class Error(val message:String): CartUiState()
    data object Loading : CartUiState()

}

