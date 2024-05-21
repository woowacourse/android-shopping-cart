package woowacourse.shopping.domain

class ShoppingCart() {
    private val _products: MutableList<Product> = mutableListOf()
    val products: List<Product> get() = _products

    fun addProduct(product: Product) {
        _products.add(product)
    }

    fun deleteProduct(itemPosition: Int) {
        _products.removeAt(itemPosition)
    }
}
