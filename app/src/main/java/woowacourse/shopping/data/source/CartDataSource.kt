package woowacourse.shopping.data.source

import woowacourse.shopping.data.source.local.cart.CartItemEntity

interface CartDataSource {
    suspend fun getCartItems(
        offset: Int,
        count: Int,
    ): List<CartItemEntity>

    suspend fun add(cartItem: CartItemEntity)

    suspend fun deleteItem(productId: String)

    suspend fun updateItem(cartItem: CartItemEntity)

    suspend fun getCartItemById(productId: String): CartItemEntity?

    suspend fun getTotalCount(): Int
}
