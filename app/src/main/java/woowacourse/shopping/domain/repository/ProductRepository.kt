package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products

interface ProductRepository {
    suspend fun getProductById(id: String): Product

    suspend fun getProductsByIds(ids: List<String>): List<Product>

    suspend fun getProducts(
        offset: Int,
        pageSize: Int,
    ): Products
}
