package woowacourse.shopping

import woowacourse.shopping.domain.ShoppingCart
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.productlist.UpdatedQuantity

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

    fun cartItemQuantity(): List<UpdatedQuantity>

    fun cartItemQuantity(productIds: Set<Long>): List<UpdatedQuantity>
}
