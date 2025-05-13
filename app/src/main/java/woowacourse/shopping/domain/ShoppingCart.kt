package woowacourse.shopping.domain

class ShoppingCart(
    products: List<Product>,
) {
    private val _products: MutableList<Product> = products.toMutableList()
    val products get() = _products.toList()

    val allPrice get() = products.sumOf { it.price }
}
