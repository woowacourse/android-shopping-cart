package woowacourse.shopping.repository

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentlyViewedProduct

interface RecentlyViewedProductRepository {
    fun findAll(): List<Product>
    fun save(product: Product)

    fun save(recentlyViewedProduct: RecentlyViewedProduct)
    fun findFirst10OrderByViewedTimeDesc(): List<RecentlyViewedProduct>
}
