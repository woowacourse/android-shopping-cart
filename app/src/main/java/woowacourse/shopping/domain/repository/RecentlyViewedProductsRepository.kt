package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.RecentlyViewedProduct

interface RecentlyViewedProductsRepository {
    fun insertRecentlyViewedProduct(product: RecentlyViewedProduct)

    fun getRecentlyViewedProducts(limit: Int): List<RecentlyViewedProduct>
}
