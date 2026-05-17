package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

interface ProductRepository {
    suspend fun getProductSize(): Int

    suspend fun getProductById(id: String): Product

    suspend fun getProducts(
        startIndex: Int,
        count: Int,
    ): List<Product>
}
