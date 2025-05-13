package woowacourse.shopping.domain.model

class ShoppingCart(
    products: List<Product>,
) {
    private val _products = products.toMutableList()
    val products: List<Product> get() = _products.toList()

    fun deleteProduct(productId: Long) {
        _products.removeIf { it.id == productId }
    }
}
