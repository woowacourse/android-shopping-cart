package woowacourse.shopping.data.cart

import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Quantity
import java.lang.IllegalArgumentException
import kotlin.math.min

class DummyCartRepository private constructor() : CartRepository {
    private val cart: MutableList<CartItem> = mutableListOf()
    private var id: Long = 0L

    override fun increaseQuantity(product: Product) {
        var oldProductQuantity = product.quantity
        val newProduct = product.copy(quantity = ++oldProductQuantity)

        val oldCartItem = cart.find { it.product.id == product.id }
        if (oldCartItem == null) {
            cart.add(CartItem(id++, newProduct))
            return
        }
        cart.remove(oldCartItem)
        cart.add(oldCartItem.copy(product = newProduct))
    }

    override fun decreaseQuantity(product: Product) {
        var oldProductQuantity = product.quantity
        val newProduct = product.copy(quantity = --oldProductQuantity)

        val oldCartItem = cart.find { it.product.id == product.id }
        oldCartItem ?: throw IllegalArgumentException(CANNOT_DELETE_MESSAGE)
        cart.remove(oldCartItem)
        if (oldProductQuantity.isMin()) {
            return
        }
        cart.add(oldCartItem.copy(product = newProduct))
    }

    override fun changeQuantity(
        product: Product,
        quantity: Quantity,
    ) {
        val newProduct = product.copy(quantity = quantity)
        val oldCartItem = cart.find { it.product.id == product.id }
        if (oldCartItem == null) {
            cart.add(CartItem(id++, newProduct))
            return
        }
        cart.add(oldCartItem.copy(product = newProduct))
        cart.remove(oldCartItem)
    }

    override fun deleteCartItem(cartItem: CartItem) {
        cart.remove(cartItem)
    }

    override fun findRange(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, cart.size)
        return cart.subList(fromIndex, toIndex)
    }

    override fun totalProductCount(): Int = cart.size

    override fun totalQuantityCount(): Int {
        return cart.fold(0) { total, cartItem ->
            total + cartItem.product.quantity.count
        }
    }

    companion object {
        private const val CANNOT_DELETE_MESSAGE = "삭제할 수 없습니다."

        @Volatile
        private var instance: DummyCartRepository? = null

        fun getInstance(): DummyCartRepository {
            return instance ?: synchronized(this) {
                DummyCartRepository()
            }
        }
    }
}
