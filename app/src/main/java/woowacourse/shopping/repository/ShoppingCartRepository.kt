package woowacourse.shopping.repository

import woowacourse.shopping.model.ShoppingCartItem

interface ShoppingCartRepository {
    suspend fun addItemToCart(productId: String, amount: Int)

    suspend fun decreaseItemQuantity(
        productId: String,
        amount: Int,
    )

    suspend fun removeItemFromCart(productId: String)

    suspend fun getCartItem(productId: String): ShoppingCartItem?

    suspend fun getCartItems(
        offset: Int,
        size: Int,
    ): List<ShoppingCartItem>

    suspend fun getTotalQuantity(): Int

    suspend fun getTotalSize(): Int
}
