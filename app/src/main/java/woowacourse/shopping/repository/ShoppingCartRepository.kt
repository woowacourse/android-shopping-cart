package woowacourse.shopping.repository

import woowacourse.shopping.model.ShoppingCartItem

interface ShoppingCartRepository {
    suspend fun addIfAbsent(productId: Long)

    suspend fun remove(shoppingCartItem: ShoppingCartItem)

    suspend fun getShoppingItems(): List<ShoppingCartItem>

    suspend fun removeByProductId(productId: Long): Boolean
}
