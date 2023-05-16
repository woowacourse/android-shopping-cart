package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.RecentProduct

interface RecentProductRepository {
    fun add(recentProduct: RecentProduct)
    fun getPartially(size: Int): List<RecentProduct>
}
