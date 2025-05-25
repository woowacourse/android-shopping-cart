package woowacourse.shopping.domain.model

data class CartItem(
    val product: Product,
    val quantity: Int,
) {
    val totalPrice: Int = product.price * quantity
}
