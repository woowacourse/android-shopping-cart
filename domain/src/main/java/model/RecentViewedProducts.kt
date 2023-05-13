package model

class RecentViewedProducts(
    products: List<Product>,
    private val maxSize: Int = 10,
) {

    private val _values: MutableList<Product> = products.toMutableList()
    val values: List<Product>
        get() = _values.toList()

    fun add(product: Product): Product? {
        refreshLatestProduct(product)

        if (_values.size > maxSize) {
            val removingProduct = values.last()

            _values.remove(removingProduct)

            return removingProduct
        }
        return null
    }

    private fun refreshLatestProduct(product: Product) {
        _values.remove(product)
        _values.add(0, product)
    }
}
