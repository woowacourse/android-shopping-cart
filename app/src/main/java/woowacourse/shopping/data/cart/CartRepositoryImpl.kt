package woowacourse.shopping.data.cart

import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Quantity
import java.lang.IllegalArgumentException
import kotlin.math.min

object CartRepositoryImpl : CartRepository {
    private val cart: MutableList<CartItem> = mutableListOf()
    private var id: Long = 0L

    private const val CANNOT_DELETE_MESSAGE = "삭제할 수 없습니다."

    override fun add(productId: Long) {
        val oldCartItem = cart.find { it.productId == productId }
        if (oldCartItem == null) {
            cart.add(CartItem(id++, productId, Quantity()))
            return
        }
        var quantity = oldCartItem.quantity
        cart.remove(oldCartItem)
        cart.add(oldCartItem.copy(quantity = ++quantity))
    }

    override fun delete(productId: Long) {
        val oldCartItem = cart.find { it.productId == productId }
        oldCartItem ?: throw IllegalArgumentException(CANNOT_DELETE_MESSAGE)
        cart.remove(oldCartItem)
        if (oldCartItem.quantity.isMin()) {
            return
        }
        var quantity = oldCartItem.quantity
        cart.add(oldCartItem.copy(quantity = --quantity))
    }

    override fun deleteAll(productId: Long) {
        cart.removeIf { it.productId == productId }
    }

    override fun findAll(): List<CartItem> {
        return cart.toList()
    }

    override fun findRange(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, cart.size)
        return cart.subList(fromIndex, toIndex)
    }

    override fun count(): Int = cart.size
}
