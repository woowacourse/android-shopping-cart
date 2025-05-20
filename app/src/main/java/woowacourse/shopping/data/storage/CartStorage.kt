package woowacourse.shopping.data.storage

import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartSinglePage

@Suppress("ktlint:standard:max-line-length")
object CartStorage {
    private val cart = LinkedHashMap<Long, Cart>()

    private val cartValues get() = cart.values.toList()

    operator fun get(id: Long) = cart[id]

    fun insert(item: Cart) {
        cart[item.id] = item
    }

    fun delete(cardId: Long) {
        cart.remove(cardId)
    }

    fun singlePage(
        fromIndex: Int,
        toIndex: Int,
    ): CartSinglePage {
        val endIndex = minOf(toIndex, carts.size)

        val result = cartValues.subList(fromIndex, endIndex)
        val hasNextPage = endIndex < cart.size

        return CartResult(result, hasNextPage)
    }
}
