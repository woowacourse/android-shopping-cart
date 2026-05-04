package woowacourse.shopping.repository.inmemory

import woowacourse.shopping.model.ProductId
import woowacourse.shopping.repository.CartRepository

object InMemoryCartRepository : CartRepository {
    private val items = mutableMapOf<ProductId, Int>()

    override suspend fun add(item: ProductId) {
        items.merge(item, 1, Int::plus)
    }

    override suspend fun delete(item: ProductId) {
        require(items.containsKey(item)) { "해당 상품은 장바구니에 존재하지 않습니다." }

        items.merge(item, 1, Int::minus)
        if (items.getValue(item) == 0) items.remove(item)
    }

    override suspend fun getCartItems(
        fromIndex: Int,
        limit: Int,
    ): Map<ProductId, Int> =
        items.entries
            .drop(fromIndex)
            .take(limit)
            .associate { it.toPair() }

    override suspend fun count(): Int = items.size
}
