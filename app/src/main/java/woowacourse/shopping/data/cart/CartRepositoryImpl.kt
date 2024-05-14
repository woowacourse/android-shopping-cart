package woowacourse.shopping.data.cart

import woowacourse.shopping.model.Quantity
import java.lang.IllegalArgumentException

typealias Cart = MutableMap<Long, Quantity>

object CartRepositoryImpl : CartRepository {
    private val cart: Cart = mutableMapOf()

    private const val CANNOT_DELETE_MESSAGE = "삭제할 수 없습니다."

    override fun add(productId: Long) {
        var quantity = cart.getOrDefault(productId, Quantity(0))
        cart[productId] = ++quantity
    }

    override fun delete(productId: Long) {
        var quantity = cart[productId] ?: throw IllegalArgumentException(CANNOT_DELETE_MESSAGE)
        cart[productId] = --quantity
    }

    override fun deleteAll(productId: Long) {
        cart.remove(productId)
    }

    override fun findAll(): Map<Long, Quantity> {
        return cart.toMap()
    }
}
