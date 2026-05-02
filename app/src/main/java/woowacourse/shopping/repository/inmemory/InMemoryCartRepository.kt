package woowacourse.shopping.repository.inmemory

import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository

object InMemoryCartRepository : CartRepository {
    private val items = mutableMapOf<Product, Int>()

    override suspend fun add(item: Product) {
        items.merge(item, 1, Int::plus)
    }

    override suspend fun delete(item: Product) {
        require(items.containsKey(item)) { "해당 상품은 장바구니에 존재하지 않습니다." }

        items.merge(item, 1, Int::minus)
        if (items.getValue(item) == 0) items.remove(item)
    }

    override suspend fun getCartItems(fromIndex: Int, limit: Int): Map<Product, Int> {
        return items.entries
            .drop(fromIndex)
            .take(limit)
            .associate { it.toPair() }
    }
    override suspend fun showAll() = Cart(items.toMap())

    override suspend fun count(): Int = items.size
}
