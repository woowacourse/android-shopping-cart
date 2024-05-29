package woowacourse.shopping

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.QuantityUpdate
import woowacourse.shopping.domain.ShoppingCart
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.domain.User
import woowacourse.shopping.productlist.UpdatedQuantity
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

    override fun addShoppingCartItem(
        product: Product,
        quantity: Int,
    ) {
        val newCartItem = ShoppingCartItem(product, quantity)
        val shoppingCart = users.first().shoppingCart
        updateShoppingCart(shoppingCart.addItem(newCartItem))
    }

    override fun deleteShoppingCartItem(productId: Long) {
        val shoppingCart = users.first().shoppingCart
        val updatedShoppingCart = shoppingCart.deleteItemById(productId)
        updateShoppingCart(updatedShoppingCart)
    }

    override fun plusCartItemQuantity(productId: Long): ShoppingCartItem {
        val shoppingCart = users.first().shoppingCart
        val cartItem = shoppingCart.items.first { it.product.id == productId }
        val result = cartItem.increaseQuantity()
        if (result is QuantityUpdate.Success) {
            updateShoppingCart(shoppingCart.updateItem(result.value))
            return result.value
        } else {
            error("주문 가능한 최대 수량을 초과했습니다.")
        }
    }

    override fun minusCartItemQuantity(productId: Long): ShoppingCartItem {
        val shoppingCart = users.first().shoppingCart
        val cartItem = shoppingCart.items.first { it.product.id == productId }
        val result = cartItem.decreaseQuantity()
        if (result is QuantityUpdate.Success) {
            updateShoppingCart(shoppingCart.updateItem(result.value))
            return result.value
        } else {
            error("주문 가능한 최소 개수는 1개 입니다.")
        }
    }

    override fun updateShoppingCart(shoppingCart: ShoppingCart) {
        this.users =
            users.map {
                if (it.id == userId()) it.copy(shoppingCart = shoppingCart) else it
            }
    }

    override fun shoppingCartSize(): Int = users.first().shoppingCart.items.size

    override fun cartTotalItemQuantity(): Int = shoppingCart(userId()).totalItemQuantity()

    override fun cartItemQuantity(): List<UpdatedQuantity> {
        val cartItems = users.first().shoppingCart.items
        return cartItems.map { item ->
            UpdatedQuantity(item.product.id, item.quantity)
        }
    }

    override fun cartItemQuantity(productIds: Set<Long>): List<UpdatedQuantity> {
        val cartItems = users.first().shoppingCart.items
        val cartItemMap = cartItems.associateBy { it.product.id }

        return productIds.map { id ->
            val quantity = cartItemMap[id]?.quantity ?: 0
            UpdatedQuantity(id, quantity)
        }
    }
}
