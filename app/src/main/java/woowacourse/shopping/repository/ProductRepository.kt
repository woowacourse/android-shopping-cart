package woowacourse.shopping.repository

import woowacourse.shopping.model.Product

interface ProductRepository {
    suspend fun getSize(): Int

    suspend fun getProducts(
        fromIndex: Int,
        loadSize: Int,
    ): List<Product>

    suspend fun hasNext(current: Int): Boolean
}
