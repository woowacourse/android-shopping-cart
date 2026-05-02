package woowacourse.shopping.repository

import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product

interface CartRepository {
    suspend fun add(item: Product)

    suspend fun delete(item: Product)
    suspend fun getCartItems(
        fromIndex: Int,
        limit: Int,
    ): Map<Product, Int>

    suspend fun showAll(): Cart

    suspend fun count(): Int
}
