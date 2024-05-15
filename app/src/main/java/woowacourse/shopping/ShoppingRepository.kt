package woowacourse.shopping

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingCart
import woowacourse.shopping.domain.ShoppingCartItem

interface ShoppingRepository {
    fun products(): List<Product>

    fun productById(id: Long): Product

    fun userId(): Long

    fun shoppingCart(userId: Long): ShoppingCart

    fun shoppingCartItems(
        page: Int,
        pageSize: Int,
    ): List<ShoppingCartItem>

    fun deleteShoppingCartItem(productId: Long)

    fun shoppingCartSize(): Int

    fun updateShoppingCart(shoppingCart: ShoppingCart)
}
