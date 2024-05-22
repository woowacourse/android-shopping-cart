package woowacourse.shopping

import woowacourse.shopping.domain.model.RecentlyProduct
import woowacourse.shopping.domain.repository.RecentlyProductRepository

class MockRecentlyProductRepository : RecentlyProductRepository {
    override fun addRecentlyProduct(recentlyProduct: RecentlyProduct) {}

    override fun getMostRecentlyProduct(): RecentlyProduct {
        return RecentlyProduct.defaultRecentlyProduct
    }

    override fun getRecentlyProductList(): List<RecentlyProduct> {
        return listOf(RecentlyProduct.defaultRecentlyProduct)
    }

    override fun deleteRecentlyProduct(id: Long) {}
}
