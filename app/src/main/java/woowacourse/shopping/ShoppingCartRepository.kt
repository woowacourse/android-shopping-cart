package woowacourse.shopping

import woowacourse.shopping.domain.Product
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

    fun addShoppingCartItem(
        product: Product,
        quantity: Int,
    )

    fun deleteShoppingCartItem(productId: Long)

    fun plusCartItemQuantity(productId: Long): ShoppingCartItem

    fun minusCartItemQuantity(productId: Long): ShoppingCartItem

    fun updateShoppingCart(shoppingCart: ShoppingCart)

    fun shoppingCartSize(): Int

    fun cartTotalItemQuantity(): Int

    fun cartItemQuantity(): List<UpdatedQuantity>

    fun cartItemQuantity(productIds: Set<Long>): List<UpdatedQuantity>
}
