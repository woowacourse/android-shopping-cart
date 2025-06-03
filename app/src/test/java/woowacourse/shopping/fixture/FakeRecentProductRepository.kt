package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.repository.RecentProductRepository

class FakeRecentProductRepository : RecentProductRepository {
    private val recentProducts = mutableListOf<RecentProduct>()

    override fun getLastViewedProduct(onSuccess: (RecentProduct?) -> Unit) {
        onSuccess(recentProducts.lastOrNull())
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
        onSuccess: (List<RecentProduct>) -> Unit,
    ) {
        onSuccess(
            recentProducts
                .sortedByDescending { it.viewedAt }
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
