package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.cart.CartItems
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.math.min

object CartRepositoryImpl : CartRepository {
    private var currentCart = CartItems()

    override fun getCartItems(page: Int, pageSize: Int): CartItems {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, currentCart.items.size)

        if (fromIndex > toIndex) return CartItems(emptyList())

        val result = currentCart.items.subList(fromIndex, toIndex)

        return CartItems(result)
    }

    override fun saveCartItems(cartItems: CartItems) {
        currentCart = cartItems
    }
}
