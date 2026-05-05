package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

interface ProductRepository {
    val productSize: Int

    fun getProductById(id: String): Product

    suspend fun getProducts(
        startIndex: Int,
        count: Int,
    ): List<Product>
}
