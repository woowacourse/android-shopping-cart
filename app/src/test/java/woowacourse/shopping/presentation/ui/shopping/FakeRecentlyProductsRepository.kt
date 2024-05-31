package woowacourse.shopping.presentation.ui.shopping

import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.domain.repository.RecentlyViewedProductsRepository

class FakeRecentlyProductsRepository : RecentlyViewedProductsRepository {
    override fun insertRecentlyViewedProduct(product: RecentlyViewedProduct) {
        return
    }

    override fun getRecentlyViewedProducts(limit: Int): Result<List<RecentlyViewedProduct>> {
        return runCatching { emptyList() }
    }
}
