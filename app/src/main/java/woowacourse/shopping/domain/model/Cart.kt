package woowacourse.shopping.domain.model

class Cart(
    initialProducts: MutableList<Product> = mutableListOf<Product>(),
) {
    private val _products: MutableList<Product> = initialProducts
    val products: List<Product> get() = _products.toList()

    fun addProduct(product: Product) {
        _products.add(product)
    }

    fun removeProduct(product: Product) {
        _products.remove(product)
    }
}
