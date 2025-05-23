package woowacourse.shopping.domain

class RecentProducts {
    private val _items = mutableListOf<RecentProduct>()

    val items: List<RecentProduct>
        get() = _items.sortedByDescending { it.viewTime }

    fun add(product: RecentProduct) {
        if (!contains(product.product)) {
            _items.add(product)
        }

        if (items.size > MAX_RECENT_PRODUCTS) {
            _items.remove(items.last())
        }
    }

    fun contains(product: Product): Boolean {
        return items.any { it.product.id == product.id }
    }

    fun isFull(): Boolean {
        return items.size == MAX_RECENT_PRODUCTS
    }

    companion object {
        private const val MAX_RECENT_PRODUCTS = 10
    }
}
