package com.devbymeco.cursotestingandroid.core.fakes

import android.util.Log.e
import com.devbymeco.cursotestingandroid.productlist.domain.model.Promotion
import com.devbymeco.cursotestingandroid.productlist.domain.repository.PromotionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow

class FakePromotionRepository : PromotionRepository {
    private val _promotions = MutableStateFlow<List<Promotion>>(emptyList())

    fun setPromotions(promotions: List<Promotion>){
        _promotions.value = promotions
    }

    override fun getActivePromotions(): Flow<List<Promotion>> = _promotions.asStateFlow()


    override suspend fun refreshPromotions() {}
}