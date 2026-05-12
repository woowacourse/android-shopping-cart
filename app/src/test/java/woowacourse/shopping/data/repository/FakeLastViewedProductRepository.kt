package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.LastViewedProductRepository

class FakeLastViewedProductRepository(
    private var lastViewedProduct: Product? = null,
) : LastViewedProductRepository {
    override suspend fun getLastViewedProduct(): Product? = lastViewedProduct

    override suspend fun saveLastViewedProduct(product: Product) {
        lastViewedProduct = product
    }
}
