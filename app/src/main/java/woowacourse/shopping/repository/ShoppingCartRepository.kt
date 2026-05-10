package woowacourse.shopping.repository

import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ShoppingCartItem

interface ShoppingCartRepository {
    suspend fun add(product: Product)

    suspend fun getTotalSize(): Int

    suspend fun remove(shoppingCartItemId: String)

    suspend fun getShoppingItem(shoppingCartItemId: String): ShoppingCartItem?

    suspend fun getShoppingItems(
        offset: Int,
        size: Int,
    ): List<ShoppingCartItem>
}
