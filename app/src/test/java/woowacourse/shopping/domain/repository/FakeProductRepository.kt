package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.history.ProductHistory
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.data.model.product.CartableProduct
import woowacourse.shopping.data.model.product.Product
import woowacourse.shopping.domain.repository.product.ProductRepository
import woowacourse.shopping.fixture.getFixtureCartableProducts
import woowacourse.shopping.fixture.getFixtureRecentProducts
import kotlin.math.min

class FakeProductRepository : ProductRepository {
    private val cartableProducts: List<CartableProduct> = getFixtureCartableProducts(100)
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

    override fun fetchSinglePage(page: Int): List<CartableProduct> {
        val fromIndex = page * 20
        val toIndex = min(fromIndex + 20, cartableProducts.size)
        return cartableProducts.subList(fromIndex, toIndex)
    }

    override fun fetchProduct(id: Long): CartableProduct {
        return cartableProducts.first { it.product.id == id }
    }
}
