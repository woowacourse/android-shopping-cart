package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository

fun fakeCartRepository() =
    object : CartRepository {
        private val cart = MutableList(10) { CartItem(it.toLong(), it.toLong(), 1) }
        private var nextId = 1L

        override fun getCartItems(
            limit: Int,
            offset: Int,
            callback: (List<CartItem>, Boolean) -> Unit,
        ) {
            val endIndex = (offset + limit).coerceAtMost(cart.size)
            val cartItems = cart.subList(offset, endIndex)
            val hasMore = endIndex < cart.size
            callback(cartItems, hasMore)
        }

        override fun deleteCartItem(
            id: Long,
            callback: (Long) -> Unit,
        ) {
            val removed = cart.removeIf { it.id == id }
            if (removed) callback(id)
        }

        override fun addCartItem(
            product: Product,
            callback: () -> Unit,
        ) {
            val cartItem = CartItem(nextId++, product.id, 1)
            cart.add(cartItem)
            callback()
        }
    }
