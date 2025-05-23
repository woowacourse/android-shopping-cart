package woowacourse.shopping.domain

class RecentProducts {
    private val _items = mutableListOf<RecentProduct>()

    val items: List<RecentProduct>
        get() = _items.toList().sortedBy { it.viewTime }

    fun add(product: RecentProduct) {
        if (items.size > MAX_RECENT_PRODUCTS) {
            _items.removeAt(items.size - 1)
        }
        _items.add(product)
    }

    fun contains(product: Product): Boolean {
        return items.any { it.product == product }
    }

    fun isFull(): Boolean {
        return items.size == MAX_RECENT_PRODUCTS
    }

    companion object {
        private const val MAX_RECENT_PRODUCTS = 10
    }
}
