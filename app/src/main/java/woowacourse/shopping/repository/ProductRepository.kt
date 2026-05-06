package woowacourse.shopping.repository

import woowacourse.shopping.model.Product

interface ProductRepository {
    suspend fun getSize(): Int

    suspend fun getProducts(
        fromIndex: Int,
        count: Int,
    ): List<Product>

    suspend fun hasNext(current: Int): Boolean
}
