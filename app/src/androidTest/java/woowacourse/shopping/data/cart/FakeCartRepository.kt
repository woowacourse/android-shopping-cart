package woowacourse.shopping.data.cart

import woowacourse.shopping.data.cart.entity.CartItem
import woowacourse.shopping.model.Quantity
import java.lang.IllegalArgumentException
import kotlin.math.min

class FakeCartRepository(savedCartItems: List<CartItem> = emptyList()) : CartRepository {
    private val cart: MutableList<CartItem> = savedCartItems.toMutableList()
    private var id: Long = 0L

    override fun increaseQuantity(productId: Long) {
        val oldCartItem = cart.find { it.productId == productId }
        if (oldCartItem == null) {
            cart.add(CartItem(id++, productId, Quantity(1)))
            return
        }
        cart.remove(oldCartItem)
        var quantity = oldCartItem.quantity
        cart.add(oldCartItem.copy(quantity = ++quantity))
    }

    override fun decreaseQuantity(productId: Long) {
        val oldCartItem = cart.find { it.productId == productId }
        oldCartItem ?: throw IllegalArgumentException()

        cart.remove(oldCartItem)
        if (oldCartItem.quantity.count == 1) {
            return
        }
        var quantity = oldCartItem.quantity
        cart.add(oldCartItem.copy(quantity = --quantity))
    }

    override fun changeQuantity(
        productId: Long,
        quantity: Quantity,
    ) {
        val oldCartItem = cart.find { it.productId == productId }
        if (oldCartItem == null) {
            cart.add(CartItem(id++, productId, quantity))
            return
        }
        cart.remove(oldCartItem)
        cart.add(oldCartItem.copy(quantity = quantity))
    }

    override fun deleteCartItem(productId: Long) {
        val deleteCartItem =
            cart.find { it.productId == productId } ?: throw IllegalArgumentException()
        cart.remove(deleteCartItem)
    }

    override fun find(productId: Long): CartItem {
        return cart.find { it.productId == productId } ?: throw IllegalArgumentException()
    }

    override fun findRange(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, cart.size)
        return cart.subList(fromIndex, toIndex)
    }

    override fun totalCartItemCount(): Int = cart.size
}
