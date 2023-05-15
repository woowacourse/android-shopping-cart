package model

data class ShoppingCartProduct(
    val product: Product,
    val count: Int = 1,
    val price: Price = product.price * count
)
