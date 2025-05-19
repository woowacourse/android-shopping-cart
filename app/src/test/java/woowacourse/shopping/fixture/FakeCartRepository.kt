package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository

fun fakeCartRepository() =
    object : CartRepository {
        private val cart =
            mutableListOf<CartItem>().apply {
                repeat(10) { add(CartItem(it.toLong(), dummyProductsFixture[it], 1)) }
            }
        private var nextId = cart.size.toLong() + 1

        override fun getCartItems(
            limit: Int,
            offset: Int,
            onResult: (Result<PageableItem<CartItem>>) -> Unit,
        ) {
            val endIndex = (offset + limit).coerceAtMost(cart.size)
            val items = cart.subList(offset, endIndex)
            val hasMore = endIndex < cart.size
            onResult(Result.success(PageableItem(items, hasMore)))
        }

        override fun deleteCartItem(
            id: Long,
            onResult: (Result<Long>) -> Unit,
        ) {
            val removed = cart.removeIf { it.id == id }
            if (removed) {
                onResult(Result.success(id))
            } else {
                onResult(Result.failure(NoSuchElementException()))
            }
        }

        override fun addCartItem(
            product: Product,
            onResult: (Result<Unit>) -> Unit,
        ) {
            val cartItem = CartItem(nextId++, product, 1)
            cart.add(cartItem)
            onResult(Result.success(Unit))
        }
    }
