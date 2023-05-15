package woowacourse.shopping

class Products(products: List<Product> = listOf()) {
    private val _items = products.toMutableList()
    val items get() = _items.toList()

    val size get(): Int = _items.size

    fun getProductsInRange(startIndex: Int, productSize: Int): Products {
        if (startIndex + productSize > size) return Products(_items.subList(startIndex, size))
        return Products(_items.subList(startIndex, startIndex + productSize))
    }

    fun addProducts(products: List<Product>) {
        _items.addAll(products)
    }

    fun deleteProduct(productId: Int) {
        _items.removeAll { it.id == productId }
    }
}
