package woowacourse.shopping.repository

import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.ProductId

class FakeCartRepository : CartRepository {
    private var cart = Cart(emptyList())

    override suspend fun add(item: ProductId) {
        cart = cart.add(item)
    }

    override suspend fun delete(item: ProductId) {
        cart = cart.delete(item)
    }

    override suspend fun getCartItems(
        fromIndex: Int,
        limit: Int,
    ): List<CartItem> {
        val safeFrom = fromIndex.coerceIn(0, cart.items.size)
        val safeLimit = limit.coerceAtLeast(0)
        val safeTo = minOf(safeFrom + safeLimit, cart.items.size)

        return cart.items.subList(safeFrom, safeTo)
    }

    override suspend fun getCartItemsByProductIds(productIds: Set<ProductId>): List<CartItem> =
        cart.items.filter { it.productId in productIds }

    override suspend fun count(): Int = cart.count()
}
