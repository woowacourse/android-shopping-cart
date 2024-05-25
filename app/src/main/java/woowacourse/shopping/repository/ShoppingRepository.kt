package woowacourse.shopping.repository

import woowacourse.shopping.domain.QuantityUpdate
import woowacourse.shopping.domain.ShoppingCart
import woowacourse.shopping.domain.ShoppingCartItem

interface ShoppingRepository {
    fun shoppingCart(): ShoppingCart

    fun shoppingCartItems(
        page: Int,
        pageSize: Int,
    ): List<ShoppingCartItem>

    fun cartItemByProductId(productId: Long): ShoppingCartItem

    fun cartItemsByProductIds(productId: List<Long>): List<ShoppingCartItem>

    fun shoppingCartItemByPosition(
        currentPage: Int,
        pageSize: Int,
        position: Int,
    ): ShoppingCartItem

    fun deleteShoppingCartItem(productId: Long)

    fun shoppingCartSize(): Int

    fun updateShoppingCart(shoppingCart: ShoppingCart)

    fun updateCartItem(updateCartItem: ShoppingCartItem)

    fun increasedCartItem(productId: Long): QuantityUpdate

    fun decreasedCartItem(productId: Long): QuantityUpdate

    fun addCartItem(shoppingCartItem: ShoppingCartItem)
}
