package woowacourse.shopping

class Products(products: List<Product> = listOf()) {
    private val _items = products.toMutableList()
    val items get() = _items.toList()

    val size get(): Int = _items.size

    fun addProducts(products: List<Product>) {
        _items.addAll(products)
    }
}
