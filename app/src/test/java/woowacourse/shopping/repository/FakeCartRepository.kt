package woowacourse.shopping.repository

import woowacourse.shopping.model.ProductId

class FakeCartRepository : CartRepository {
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
    ): Map<ProductId, Int> {
        val safeFrom = fromIndex.coerceIn(0, items.size)
        val safeLimit = limit.coerceAtLeast(0)

        return items.entries
            .drop(safeFrom)
            .take(safeLimit)
            .associate { it.toPair() }
    }

    override suspend fun count(): Int = items.size
}
