package woowacourse.shopping

import woowacourse.shopping.domain.QuantityUpdate
import woowacourse.shopping.domain.ShoppingCart
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.domain.User
import kotlin.math.min

object DummyShoppingRepository : ShoppingRepository {
    private var users =
        listOf(
            User(
                id = 0L,
                shoppingCart = ShoppingCart(emptyList()),
            ),
        )

    override fun shoppingCart(): ShoppingCart = users.first { it.id == users.first().id }.shoppingCart

    override fun shoppingCartItems(
        page: Int,
        pageSize: Int,
    ): List<ShoppingCartItem> {
        val cartItems = users.first().shoppingCart.items
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, cartItems.size)
        return cartItems.subList(fromIndex, toIndex)
    }

    override fun cartItemByProductId(productId: Long): ShoppingCartItem =
        shoppingCart().items.firstOrNull { it.product.id == productId }
            ?: error("$productId 에 해당하는 product가 없습니다.")

    override fun cartItemsByProductIds(productIds: List<Long>): List<ShoppingCartItem> =
        shoppingCart().items.filter {
            productIds.contains(it.product.id)
        }

    override fun shoppingCartItemByPosition(
        currentPage: Int,
        pageSize: Int,
        position: Int,
    ): ShoppingCartItem {
        val items = shoppingCartItems(currentPage, pageSize)
        return items.elementAtOrNull(position) ?: error("$position : 해당 위치에 해당하는 item이 없습니다.")
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
                if (it.id == users.first().id) it.copy(shoppingCart = shoppingCart) else it
            }
    }

    override fun increasedCartItem(productId: Long): QuantityUpdate {
        val cartItem =
            shoppingCart().items.firstOrNull { it.product.id == productId }
                ?: error("$productId 에 해당하는 product가 없습니다.")
        val updateResult = cartItem.increaseQuantity()
        if (updateResult is QuantityUpdate.Success) updateCartItem(updateResult.value)
        return updateResult
    }

    override fun updateCartItem(updatedCartItem: ShoppingCartItem) {
        val shoppingCart = shoppingCart().updateItem(updatedCartItem)

        this.users =
            users.map {
                if (it.id == users.first().id) it.copy(shoppingCart = shoppingCart) else it
            }
    }

    override fun decreasedCartItem(productId: Long): QuantityUpdate {
        val cartItem =
            shoppingCart().items.firstOrNull { it.product.id == productId }
                ?: error("$productId 에 해당하는 product가 없습니다.")
        val updateResult = cartItem.decreaseQuantity()
        if (updateResult is QuantityUpdate.Success) updateCartItem(updateResult.value)
        return updateResult
    }

    override fun addCartItem(shoppingCartItem: ShoppingCartItem) {
        val updatedShoppingCart = users.first().shoppingCart.addItem(shoppingCartItem)
        this.users =
            users.map {
                if (it.id == users.first().id) it.copy(shoppingCart = updatedShoppingCart) else it
            }
    }
}
