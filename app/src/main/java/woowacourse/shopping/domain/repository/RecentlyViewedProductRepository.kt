package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.RecentlyViewedProducts
import kotlin.uuid.ExperimentalUuidApi

interface RecentlyViewedProductRepository {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun viewProduct(product: Product)

    suspend fun getRecentlyViewedProducts(): RecentlyViewedProducts
}
