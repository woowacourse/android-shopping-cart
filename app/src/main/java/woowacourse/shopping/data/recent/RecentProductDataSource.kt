package woowacourse.shopping.data.recent

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct

interface RecentProductDataSource {
    fun recentProducts(size: Int): List<RecentProduct>
    fun addRecentProduct(product: Product, viewTime: Long)
    fun lastViewedProduct(): RecentProduct?
}
