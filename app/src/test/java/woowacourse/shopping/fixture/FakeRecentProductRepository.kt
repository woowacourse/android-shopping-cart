package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.repository.RecentProductRepository

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

    override fun replaceRecentProduct(recentProduct: RecentProduct) {
        recentProducts.remove(recentProduct)
        insert(recentProduct.product.id)
    }
}
