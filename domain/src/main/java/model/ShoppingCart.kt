package model

class ShoppingCart(
    private val products: List<ShoppingCartProduct>,
) {

    val totalPrice: Int
        get() = products.filter { it.selected }
            .sumOf { it.price.value }
}
