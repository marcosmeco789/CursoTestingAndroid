package com.devbymeco.cursotestingandroid.detail.presentation

import com.devbymeco.cursotestingandroid.productlist.domain.model.ProductWithPromotion

data class ProductDetailUiState(
    val item: ProductWithPromotion? = null,
    val isLoading: Boolean = true
)