package woowacourse.shopping

import woowacourse.shopping.domain.ShoppingCart
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.domain.User
import kotlin.math.min

object UserShoppingCartRepository : ShoppingCartRepository {
    private var users =
        listOf(
            User(
                id = 0L,
                shoppingCart = ShoppingCart(emptyList()),
            ),
        )

    override fun userId(): Long = users.first().id

    override fun shoppingCart(userId: Long): ShoppingCart =
        users.firstOrNull { it.id == userId }?.shoppingCart ?: error("$userId 에 해당하는 유저가 없습니다.")

    override fun shoppingCartItems(
        page: Int,
        pageSize: Int,
    ): List<ShoppingCartItem> {
        val cartItems = users.first().shoppingCart.items
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, cartItems.size)
        return cartItems.subList(fromIndex, toIndex)
    }

    override fun deleteShoppingCartItem(productId: Long) {
        val shoppingCart = users.first().shoppingCart
        val updatedShoppingCart = shoppingCart.deleteItemById(productId)
        updateShoppingCart(updatedShoppingCart)
    }

    override fun shoppingCartSize(): Int = users.first().shoppingCart.items.size

    override fun updateShoppingCart(shoppingCart: ShoppingCart) {
        this.users =
            users.map {
                if (it.id == userId()) it.copy(shoppingCart = shoppingCart) else it
            }
    }

    override fun cartTotalItemQuantity(): Int = shoppingCart(userId()).totalItemQuantity()
}
