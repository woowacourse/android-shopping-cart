package woowacourse.shopping.presentation.fake

import woowacourse.shopping.data.model.history.ProductHistory
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.data.model.product.Product
import woowacourse.shopping.domain.repository.history.ProductHistoryRepository
import woowacourse.shopping.fixture.getFixtureRecentProducts

class FakeProductHistoryRepository : ProductHistoryRepository {
    private val recentProducts = getFixtureRecentProducts(100).toMutableList()

    override fun addProductHistory(productHistory: ProductHistory) {
        recentProducts.add(
            RecentProduct(
                productHistory = productHistory,
                product =
                    Product(
                        productHistory.productId,
                        "사과${productHistory.productId + 1}",
                        "image${productHistory.productId + 1}",
                        1000 * (productHistory.productId.toInt() + 1),
                    ),
            ),
        )
    }

    override fun fetchProductHistory(size: Int): List<RecentProduct> {
        return recentProducts.reversed().subList(0, size)
    }

    override fun fetchLatestHistory(): RecentProduct? {
        return recentProducts.lastOrNull()
    }
}
