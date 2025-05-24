package woowacourse.shopping.fixture

import woowacourse.shopping.data.repository.recent.RecentProductsRepository
import woowacourse.shopping.domain.RecentProduct

class FakeRecentProductRepository : RecentProductsRepository {
    override fun findAll(): List<RecentProduct> {
        return TestRecentProducts.recentProducts
    }

    override fun insert(product: RecentProduct) {
        TestRecentProducts.recentProducts.add(product)
    }

    override fun update(product: RecentProduct) {
        if (TestRecentProducts.recentProducts.isNotEmpty()) {
            TestRecentProducts.recentProducts.removeLast()
        }
        TestRecentProducts.recentProducts.add(product)
    }
}
