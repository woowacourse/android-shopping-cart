package woowacourse.shopping.repository

import woowacourse.shopping.model.Product

data class ViewedProduct(
    val product: Product,
    val viewedAt: Long,
)

interface ViewedProductRepository {
    suspend fun addViewedProductByProductId(productId: String)

    suspend fun getViewedProducts(
        offset: Int,
        size: Int,
    ): List<ViewedProduct>
}
