package woowacourse.shopping.data.storage

import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartResult

@Suppress("ktlint:standard:max-line-length")
object CartStorage {
    private val cart = LinkedHashMap<Long, Cart>()

    private val cartValues get() = cart.values.toList()

    operator fun get(id: Long) = cart[id] ?: throw IllegalArgumentException()

    fun insert(item: Cart) {
        cart[item.id] = item
    }

    fun delete(id: Long) {
        cart.remove(id)
    }

    fun singlePage(
        fromIndex: Int,
        toIndex: Int,
    ): CartResult {
        val endIndex = minOf(toIndex, cart.size)

        val result = cartValues.subList(fromIndex, endIndex)
        val hasNextPage = endIndex < cart.size

        return CartResult(result, hasNextPage)
    }
}
