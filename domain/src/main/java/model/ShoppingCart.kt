package model

class ShoppingCart(
    products: List<Product>,
) {

    private val _products: MutableList<Product> = products.toMutableList()
    val products: List<Product>
        get() = _products.toList()

    fun remove(product: Product) {
        _products.remove(product)
    }

    fun add(product: Product) {
        _products.add(product)
    }
}
