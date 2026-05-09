package woowacourse.shopping.model

data class CartItem(
    val product: Product,
    val quantity: Int,
) {
    val totalPrice: Money
        get() = product.price * quantity
}
