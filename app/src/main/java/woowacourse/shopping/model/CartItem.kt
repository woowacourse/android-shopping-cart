package woowacourse.shopping.model

data class CartItem(
    val productId: Int,
    var quantity: Int
) {
    fun increaseQuantity() {
        quantity++
    }

    fun decreaseQuantity() {
        quantity--
    }
}
