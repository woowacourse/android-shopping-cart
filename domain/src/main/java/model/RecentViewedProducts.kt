package model

class RecentViewedProducts(
    products: List<RecentViewedProduct>,
    private val maxSize: Int = 10,
) {

    private val _values: MutableList<RecentViewedProduct> = products.toMutableList()
    val values: List<RecentViewedProduct>
        get() = _values.toList()

    fun add(product: RecentViewedProduct): RecentViewedProduct? {
        refreshLatestProduct(product)

        if (_values.size > maxSize) {
            val removingProduct = values.last()

            _values.remove(removingProduct)

            return removingProduct
        }
        return null
    }

    private fun refreshLatestProduct(product: RecentViewedProduct) {
        _values.remove(product)
        _values.add(0, product)
    }
}
