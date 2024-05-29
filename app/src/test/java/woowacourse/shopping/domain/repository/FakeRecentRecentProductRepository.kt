package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.db.producthistory.RecentProduct

class FakeRecentRecentProductRepository(
    private var productHistories: MutableList<RecentProduct>,
) : RecentProductRepository {
    override fun getProductHistories(): List<RecentProduct>? {
        return productHistories
    }

    override fun getMostRecentProductHistory(): RecentProduct? {
        return productHistories.last()
    }

    override fun setProductHistory(productId: Long) {
        productHistories.add(RecentProduct(6, productId))
    }
}
