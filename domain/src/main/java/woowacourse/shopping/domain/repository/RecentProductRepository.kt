package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct

interface RecentProductRepository {
    fun add(recentProduct: Product)
    fun getPartially(size: Int): List<RecentProduct>
}
