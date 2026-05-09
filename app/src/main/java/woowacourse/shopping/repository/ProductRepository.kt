package woowacourse.shopping.repository

import woowacourse.shopping.model.Product

interface ProductRepository {
    suspend fun totalSize(): Int

    suspend fun getProduct(productId: String): Product?

    suspend fun getProducts(
        offset: Int,
        size: Int,
    ): List<Product>
}
