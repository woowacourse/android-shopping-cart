package model

class RecentProducts(
    products: List<Product>,
    private val maxSize: Int,
) {

    private val _values: MutableList<Product> = products.toMutableList()
    val values: List<Product>
        get() = _values.toList()

    fun add(product: Product): Product? {
        _values.add(product)

        if (_values.size == maxSize) {
            return _values.removeFirst()
        }
        return null
    }
}
