package woowacourse.shopping.repository

import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.ProductId

interface CartRepository {
    suspend fun add(item: ProductId)

    suspend fun delete(item: ProductId)

    suspend fun getCartItems(
        fromIndex: Int,
        limit: Int,
    ): List<CartItem>

    suspend fun getCartItemsByProductIds(productIds: Set<ProductId>): List<CartItem>

    suspend fun count(): Int
}
