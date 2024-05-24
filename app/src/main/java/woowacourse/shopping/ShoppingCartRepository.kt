package woowacourse.shopping

import woowacourse.shopping.domain.ShoppingCart
import woowacourse.shopping.domain.ShoppingCartItem

interface ShoppingCartRepository {
    fun userId(): Long

    fun shoppingCart(userId: Long): ShoppingCart

    fun shoppingCartItems(
        page: Int,
        pageSize: Int,
    ): List<ShoppingCartItem>

    fun deleteShoppingCartItem(productId: Long)

    fun shoppingCartSize(): Int

    fun updateShoppingCart(shoppingCart: ShoppingCart)

    fun cartTotalItemQuantity(): Int
}
