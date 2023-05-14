package woowacourse.shopping.domain

class RecentProducts(
    _items: List<RecentProduct> = emptyList(),
    private val maxCount: Int = 10,
) {
    private val items: List<RecentProduct> = _items.take(maxCount)

    fun add(item: RecentProduct): RecentProducts {
        val newItems = items.toMutableList()
        if (newItems.size == maxCount) newItems.removeLast()
        newItems.add(0, RecentProduct(product = item.product))

        return RecentProducts(newItems.take(maxCount))
    }

    operator fun plus(item: RecentProduct): RecentProducts = add(item)

    fun getItems(): List<RecentProduct> = items.map { it }.toList()
}
