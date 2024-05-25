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
        return _items.let(::RecentProducts)
    }

    fun sortedRecentProduct(): List<RecentProduct> = _items.sortedByDescending { it.localDateTime }

    fun lastRecentProduct(): GetLastProduct =
        if (_items.isNotEmpty()) {
            GetLastProduct.Success(_items.last())
        } else {
            GetLastProduct.Fail
        }

    companion object {
        private const val MAX_SIZE = 10
    }
}

sealed interface GetLastProduct {
    data class Success(val value: RecentProduct) : GetLastProduct

    data object Fail : GetLastProduct
}
