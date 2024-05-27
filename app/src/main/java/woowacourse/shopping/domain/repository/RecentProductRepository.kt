package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.db.producthistory.RecentProduct

interface RecentProductRepository {
    fun getProductHistories(): List<RecentProduct>?

    fun getMostRecentProductHistory(): RecentProduct?

    fun setProductHistory(productId: Long)
}
