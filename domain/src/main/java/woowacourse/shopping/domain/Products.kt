package woowacourse.shopping.domain

typealias DomainProducts = Products

data class Products(
    private val items: List<Product> = emptyList(),
    private val loadUnit: Int = DEFAULT_LOAD_AT_ONCE,
) {
    val lastId: Int = items.maxOfOrNull { it.id } ?: -1
    val size: Int = items.size

    fun addAll(newItems: List<Product>): Products = copy(items = items + newItems)

//    fun add(newItem: Product): Products {
//        if (newItem !in items) return copy(items = items + newItem)
//        return copy(items = items.map { item ->
//            if (item == newItem) item.plusCount() else item
//        })
//    }
//
//    fun remove(removedItem: Product): Products {
//        if (removedItem !in items) return this
//        if (removedItem.minusCount().isEmpty()) return copy(items = items - removedItem)
//        return copy(items = items.map { item ->
//            if (item == removedItem) item.minusCount() else item
//        })
//    }

    fun canLoadMore(): Boolean =
        items.size >= loadUnit && (items.size % loadUnit >= 1 || loadUnit == 1 && items.size > loadUnit)

    fun getItems(): List<Product> = items.toList()

    fun getItemsByUnit(): List<Product> = items.take(
        (items.size / loadUnit).coerceAtLeast(1) * loadUnit
    )

    companion object {
        private const val DEFAULT_LOAD_AT_ONCE = 20
    }
}
