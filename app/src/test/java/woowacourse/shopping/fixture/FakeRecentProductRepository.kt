package woowacourse.shopping.fixture

import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct

class FakeRecentProductRepository : RecentProductRepository {
    private val recentProducts = mutableListOf<RecentProduct>()
    private var nextId = 1L

    override fun insert(productId: Long) {
        val product =
            RecentProduct(
                id = nextId++,
                Product(id = productId, imageUrl = "", name = "Product $productId", price = 1000),
            )
        recentProducts.add(product)
    }

    override fun getAll(): List<RecentProduct> = recentProducts.toList()

    override fun getLastProduct(): RecentProduct? = recentProducts.lastOrNull()

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): List<RecentProduct> =
        recentProducts
            .sortedByDescending { it.id }
            .drop(offset)
            .take(limit)

    override fun replaceRecentProduct(productId: Long) {
        recentProducts.removeAll { it.product.id == productId }
        insert(productId)
    }
}
