package woowacourse.shopping.domain

class RecentProducts(items: Collection<RecentProduct>) {
    private val _items: LinkedHashSet<RecentProduct> = items.toCollection(LinkedHashSet())
    val items: List<RecentProduct>
        get() = _items.toList()

    fun add(element: RecentProduct): RecentProducts {
        _items.remove(element)

        if (_items.size == MAX_SIZE) {
            _items.remove(_items.first())
        }
        _items.add(element)
        return _items.toList().let(::RecentProducts)
    }

    fun sortedRecentProduct(): List<RecentProduct> = _items.sortedByDescending { it.localDateTime }

    companion object {
        private const val MAX_SIZE = 10
    }
}
