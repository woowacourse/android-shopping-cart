package woowacourse.shopping.domain

data class RecentProducts(
    private val items: List<RecentProduct> = emptyList(),
    private val maxCount: Int = 10,
) {

    fun add(newItem: RecentProduct): RecentProducts {
        val newItems = items.toMutableList()
        if (newItems.size == maxCount) newItems.removeLast()
        newItems.add(0, RecentProduct(product = newItem.product))

        return RecentProducts(newItems.take(maxCount), maxCount)
    }

    fun update(newItems: RecentProducts): RecentProducts = copy(items = newItems.items)

    fun getLatest(): RecentProduct? = items.firstOrNull()

    operator fun plus(newItem: RecentProduct): RecentProducts = add(newItem)

    fun getItems(): List<RecentProduct> = items.take(maxCount).map { it.copy() }
}
