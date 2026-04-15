package com.devbymeco.cursotestingandroid.cart.presentation.model

import com.devbymeco.cursotestingandroid.cart.domain.model.CartItem
import com.devbymeco.cursotestingandroid.productlist.domain.model.ProductWithPromotion

data class CartItemWithPromotion(
    val cartItem: CartItem,
    val item: ProductWithPromotion
)