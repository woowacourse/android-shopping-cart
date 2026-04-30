package woowacourse.shopping.model

class Products(
    products: List<Product>,
) : Iterable<Product> {
    private val value = products.toList()

    override fun iterator(): Iterator<Product> = value.iterator()

    fun getPagedProducts(
        fromIndex: Int,
        limit: Int,
    ): Products {
        val safeFrom = fromIndex.coerceAtLeast(0)
        val safeTo = minOf(safeFrom + limit, value.size)

        return Products(value.subList(safeFrom, safeTo))
    }
}
