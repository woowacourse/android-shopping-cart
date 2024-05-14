package woowacourse.shopping.data.cart

import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Quantity
import java.lang.IllegalArgumentException

typealias Cart = MutableMap<Product, Quantity>

object CartRepositoryImpl : CartRepository {
    private val cart: Cart = mutableMapOf()

    private const val CANNOT_DELETE_MESSAGE = "삭제할 수 없습니다."

    override fun add(product: Product) {
        var quantity = cart.getOrDefault(product, Quantity(0))
        cart[product] = ++quantity
    }

    override fun delete(product: Product) {
        var quantity = cart[product] ?: throw IllegalArgumentException(CANNOT_DELETE_MESSAGE)
        cart[product] = --quantity
    }

    override fun findAll(): Map<Product, Quantity> {
        return cart.toMap()
    }
}
