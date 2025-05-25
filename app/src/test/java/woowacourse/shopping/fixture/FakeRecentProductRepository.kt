package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.repository.RecentProductRepository

class FakeRecentProductRepository : RecentProductRepository {
    private val recentProducts = mutableListOf<RecentProduct>()
    private var nextId = 1L

    override fun insert(
        productId: Long,
        onSuccess: () -> Unit,
    ) {
        val product =
            RecentProduct(
                id = nextId++,
                Product(id = productId, imageUrl = "", name = "Product $productId", price = 1000),
            )
        recentProducts.add(product)
        onSuccess()
    }

    override fun getLastProduct(onSuccess: (RecentProduct?) -> Unit) {
        onSuccess(recentProducts.lastOrNull())
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
        onSuccess: (List<RecentProduct>) -> Unit,
    ) {
        onSuccess(
            recentProducts
                .sortedByDescending { it.id }
                .drop(offset)
                .take(limit),
        )
    }

    override fun replaceRecentProduct(
        recentProduct: RecentProduct,
        onSuccess: () -> Unit,
    ) {
        recentProducts.removeIf { it.product.id == recentProduct.product.id }
        recentProducts.add(recentProduct)
        onSuccess()
    }
}
