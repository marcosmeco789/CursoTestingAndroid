package com.devbymeco.cursotestingandroid.productlist.presentation

sealed class ProductListUiState {
    data object Loading : ProductListUiState()
    data class Error(val message: String) : ProductListUiState()
    data class Success(
        // products: List<>,
        // categories: List<>,
        val selectedCategory: String,
        // sortOption
    ) : ProductListUiState()
}