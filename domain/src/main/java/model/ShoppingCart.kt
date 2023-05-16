package model

class ShoppingCart(
    private val products: List<ShoppingCartProduct>,
) {

    val totalPrice: Price
        get() = Price(
            products.filter { it.selected }
                .sumOf { it.price.value }
        )
}
