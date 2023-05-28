package model

class RecentViewedProducts(
    products: List<RecentViewedProduct>,
    private val maxSize: Int = 10,
) {

    private val _values: MutableList<RecentViewedProduct> = products.toMutableList()
    val values: List<RecentViewedProduct>
        get() = _values.toList()

    fun add(
        product: RecentViewedProduct,
        handleOldestViewedProduct: () -> Unit,
    ) {
        _values.remove(product)
        _values.add(0, product)

        if (_values.size > maxSize) {
            handleOldestViewedProduct()
            _values.removeLast()
        }
    }
}
