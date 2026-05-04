package woowacourse.shopping.model

class Products(
    products: List<Product>,
) : Iterable<Product> {
    private val value = products.toList()

    override fun iterator(): Iterator<Product> = value.iterator()

    fun getPagedProducts(
        fromIndex: Int,
        loadSize: Int,
    ): Products {
        val safeFrom = fromIndex.coerceAtLeast(0)
        val safeTo = minOf(safeFrom + loadSize, value.size)

        return Products(value.subList(safeFrom, safeTo))
    }
}
