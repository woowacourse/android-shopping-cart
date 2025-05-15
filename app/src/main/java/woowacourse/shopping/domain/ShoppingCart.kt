package woowacourse.shopping.domain

class ShoppingCart(
    products: List<Product>,
) {
    private val _products: MutableList<Product> = products.toMutableList()
    val products get() = _products.toList()

    val allPrice get() = products.sumOf { it.price }

    fun addProduct(product: Product) {
        _products.add(product)
    }

    fun removeProduct(product: Product) {
        _products.remove(product)
    }
}
