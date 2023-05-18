package woowacourse.shopping.domain

class RecentProducts(
    _items: List<RecentProduct> = emptyList(),
    private val maxCount: Int = 10,
) {
    private val items: List<RecentProduct> = _items.take(maxCount)

    fun add(newItem: RecentProduct): RecentProducts {
        val newItems = items.toMutableList()
        if (newItems.size == maxCount) newItems.removeLast()
        newItems.add(0, RecentProduct(product = newItem.product))

        return RecentProducts(newItems.take(maxCount), maxCount)
    }

    fun getLatest(): RecentProduct? = items.firstOrNull()

    operator fun plus(newItem: RecentProduct): RecentProducts = add(newItem)

    fun getItems(): List<RecentProduct> = items.map { it }.toList()
}
