package woowacourse.shopping.model

data class CartItem(
    val product: Product,
    val quantity: Int,
) {
    init {
        require(quantity >= 1) { "수량은 1 이상의 정수여야 합니다." }
    }

    val totalPrice: Money
        get() = product.price * quantity
}
