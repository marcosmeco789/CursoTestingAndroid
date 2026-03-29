package com.devbymeco.cursotestingandroid.productlist.presentation

import com.devbymeco.cursotestingandroid.productlist.domain.model.Product

sealed class ProductListUiState {
    data object Loading : ProductListUiState()
    data class Error(val message: String) : ProductListUiState()
    data class Success(
        val products: List<Product>,
        // categories: List<>,
        //val selectedCategory: String,
        // sortOption
    ) : ProductListUiState()



}