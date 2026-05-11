package woowacourse.shopping.repository

import woowacourse.shopping.model.Product
import java.util.UUID

interface ProductRepository {
    suspend fun getSize(): Int

    suspend fun getProducts(
        fromIndex: Int,
        count: Int,
    ): List<Product>

    suspend fun hasNext(currentIndex: Int): Boolean

    suspend fun findProduct(id: UUID): Product?
}
