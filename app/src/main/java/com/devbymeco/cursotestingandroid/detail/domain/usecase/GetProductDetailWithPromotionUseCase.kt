package com.devbymeco.cursotestingandroid.detail.domain.usecase

import com.devbymeco.cursotestingandroid.cart.domain.ex.activeAt
import com.devbymeco.cursotestingandroid.core.domain.util.Clock
import com.devbymeco.cursotestingandroid.productlist.domain.model.ProductWithPromotion
import com.devbymeco.cursotestingandroid.productlist.domain.repository.ProductRepository
import com.devbymeco.cursotestingandroid.productlist.domain.repository.PromotionRepository
import com.devbymeco.cursotestingandroid.productlist.domain.usecase.GetPromotionForProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.Instant
import javax.inject.Inject

class GetProductDetailWithPromotionUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val promotionRepository: PromotionRepository,
    private val getPromotionForProduct: GetPromotionForProduct,
    private val clock: Clock
) {

    operator fun invoke(productId:String): Flow<ProductWithPromotion?> {
        return combine(
            productRepository.getProductById(productId),
            promotionRepository.getActivePromotions()
        ){ product, promotions ->
            val now = clock.now()
            val activePromotions = promotions.activeAt(now)

            product?.let {
                val finalPromotion = getPromotionForProduct(it, activePromotions)
                ProductWithPromotion(product = it, promotion = finalPromotion)
            }
        }
    }

}