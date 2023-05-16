package woowacourse.shopping.data.recentproduct

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts

interface RecentProductDataSource {
    fun addRecentProduct(recentProduct: RecentProduct)

    fun getAll(): RecentProducts

    fun getByProduct(product: Product): RecentProduct?

    fun modifyRecentProduct(recentProduct: RecentProduct)
}
