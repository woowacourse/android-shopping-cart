package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.model.RecentProducts

interface RecentProductRepository {
    fun add(recentProduct: RecentProduct)
    fun getPartially(size: Int): RecentProducts
}
