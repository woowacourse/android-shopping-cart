package woowacourse.shopping.domain

class RecentProducts(private val maxSize: Int) {
    private val _items = LinkedHashSet<RecentProduct>()
    val items: List<RecentProduct> get() = _items.toList()

    fun add(element: RecentProduct) {
        _items.remove(element)

        if (_items.size == maxSize) {
            _items.remove(_items.first())
        }
        _items.add(element)
    }

    fun sortedRecentProduct(): List<RecentProduct> = _items.sortedByDescending { it.localDateTime }
}
