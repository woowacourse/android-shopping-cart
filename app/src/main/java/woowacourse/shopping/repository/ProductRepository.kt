package woowacourse.shopping.repository

import woowacourse.shopping.model.Products

interface ProductRepository {
    suspend fun getSize(): Int

    suspend fun getProducts(
        fromIndex: Int,
        loadSize: Int,
    ): Products

    suspend fun hasNext(current: Int): Boolean
}
