package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.RecentlyViewedProducts

interface RecentlyViewedProductRepository {
    suspend fun saveViewedProduct(product: Product)

    suspend fun getRecentlyViewedProducts(): RecentlyViewedProducts

    suspend fun getLastViewedProduct(): Product?
}
