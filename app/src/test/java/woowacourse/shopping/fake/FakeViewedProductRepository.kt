package woowacourse.shopping.fake

import woowacourse.shopping.model.ViewedProduct
import woowacourse.shopping.repository.ViewedProductRepository

class FakeViewedProductRepository : ViewedProductRepository {
    override suspend fun addViewedProduct(productId: String) = Unit

    override suspend fun getRecentlyViewedProducts(
        offset: Int,
        size: Int,
    ): List<ViewedProduct> = emptyList()
}
