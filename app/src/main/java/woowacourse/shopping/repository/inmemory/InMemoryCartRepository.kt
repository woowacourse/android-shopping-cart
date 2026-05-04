package woowacourse.shopping.repository.inmemory

import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.repository.CartRepository

object InMemoryCartRepository : CartRepository {
    private var cart = Cart(emptyMap())

    override suspend fun add(item: ProductId) {
        cart = cart.add(item)
    }

    override suspend fun delete(item: ProductId) {
        cart = cart.delete(item)
    }

    override suspend fun getCartItems(
        fromIndex: Int,
        limit: Int,
    ): Map<ProductId, Int> {
        val safeFrom = fromIndex.coerceAtLeast(0)
        val safeTo = minOf(safeFrom + limit, cart.items.size)

        return cart.items.entries
            .toList()
            .subList(safeFrom, safeTo)
            .associate { it.toPair() }
    }

    override suspend fun count(): Int = cart.count()
}
