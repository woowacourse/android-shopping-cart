package woowacourse.shopping.data

import woowacourse.shopping.domain.CartDataSource
import woowacourse.shopping.domain.CartItem
import kotlin.math.min

object Cart : CartDataSource {
    private val cartItems: MutableList<CartItem> = mutableListOf()
    private var id: Long = 1

    override fun addCartItem(cartItem: CartItem): Long {
        cartItems.add(cartItem.copy(id = id))
        return id++
    }

    override fun getCartItems(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, cartItems.size)
        return cartItems.subList(fromIndex, toIndex)
    }
}
