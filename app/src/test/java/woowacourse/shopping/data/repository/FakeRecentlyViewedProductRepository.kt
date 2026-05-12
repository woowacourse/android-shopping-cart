package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.RecentlyViewedProducts
import woowacourse.shopping.domain.repository.RecentlyViewedProductRepository

class FakeRecentlyViewedProductRepository(
    private var recentlyViewedProducts: RecentlyViewedProducts = RecentlyViewedProducts(),
) : RecentlyViewedProductRepository {
    override suspend fun viewProduct(product: Product) {
        recentlyViewedProducts = recentlyViewedProducts.add(product)
    }

    override suspend fun getRecentlyViewedProducts(): RecentlyViewedProducts = recentlyViewedProducts
}
