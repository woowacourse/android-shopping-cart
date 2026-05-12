package woowacourse.shopping.repository

import woowacourse.shopping.model.ViewedProduct

interface ViewedProductRepository {
    suspend fun addViewedProduct(productId: String)

    suspend fun getRecentlyViewedProducts(
        offset: Int,
        size: Int,
    ): List<ViewedProduct>
}
