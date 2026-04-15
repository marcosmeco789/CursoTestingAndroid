package com.devbymeco.cursotestingandroid.productlist.domain.repository

import com.devbymeco.cursotestingandroid.productlist.domain.model.Promotion
import kotlinx.coroutines.flow.Flow

interface PromotionRepository {
    fun getActivePromotions(): Flow<List<Promotion>>
    suspend fun refreshPromotions()
}