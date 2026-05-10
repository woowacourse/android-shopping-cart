package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.RecentlyViewedProducts

interface RecentlyViewedProductRepository {
    suspend fun viewProduct(product: Product)

    suspend fun getRecentlyViewedProducts(): RecentlyViewedProducts
}
