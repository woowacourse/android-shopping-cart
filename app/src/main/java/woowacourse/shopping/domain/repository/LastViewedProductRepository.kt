package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.product.Product

interface LastViewedProductRepository {
    suspend fun getLastViewedProduct(): Product?

    suspend fun saveLastViewedProduct(product: Product)
}
