package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductItems

interface ProductRepository {
    suspend fun getProducts(
        page: Int,
        pageSize: Int = 20,
    ): ProductItems

    suspend fun getProductCount(): Int

    suspend fun getProduct(id: String): Product?
}
