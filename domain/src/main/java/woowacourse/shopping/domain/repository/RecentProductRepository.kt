package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts

interface RecentProductRepository {
    fun addRecentProduct(recentProduct: RecentProduct)

    fun getAll(): RecentProducts

    fun getByProduct(product: Product): RecentProduct?

    fun modifyRecentProduct(recentProduct: RecentProduct)

    fun getLatestRecentProduct(): RecentProduct?
}
