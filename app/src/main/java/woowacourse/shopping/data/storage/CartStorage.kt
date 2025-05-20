package woowacourse.shopping.data.storage

import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartSinglePage

@Suppress("ktlint:standard:max-line-length")
object CartStorage {
    private val carts = LinkedHashMap<Long, Cart>()

    private val cartValues get() = carts.values.toList()

    operator fun get(id: Long) = carts[id]

    fun insert(item: Cart) {
        carts[item.productId] = item
    }

    fun modifyQuantity(
        cardId: Long,
        quantity: Quantity,
    ) {
        if (removeIfQuantityIsZero(cardId, quantity)) return
        val target = carts[cardId] ?: throw IllegalArgumentException()
        val result = target.copy(quantity = quantity)
        carts[cardId] = result
    }

    fun singlePage(
        fromIndex: Int,
        toIndex: Int,
    ): CartSinglePage {
        val endIndex = minOf(toIndex, carts.size)

        val result = cartValues.subList(fromIndex, endIndex)
        val hasNextPage = endIndex < carts.size

        return CartSinglePage(result, hasNextPage)
    }

    private fun removeIfQuantityIsZero(
        cartId: Long,
        quantity: Quantity,
    ): Boolean {
        if (quantity.value == 0) {
            carts.remove(cartId)
            return true
        }
        return false
    }
}
