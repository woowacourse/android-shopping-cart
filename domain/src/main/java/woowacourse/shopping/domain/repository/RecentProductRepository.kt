package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts

interface RecentProductRepository {
    fun add(recentProduct: RecentProduct)
    fun getPartially(size: Int): RecentProducts
}
