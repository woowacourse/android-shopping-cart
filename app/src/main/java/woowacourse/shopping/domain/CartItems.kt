package woowacourse.shopping.domain

data class CartItems(
    val items: List<CartItem> = emptyList(),
    val isLast: Boolean = true,
) {
    fun getCartItem(productId: String): CartItem? = items.find { it.productId == productId }

    fun getCartItemAmount(productId: String): Int = getCartItem(productId)?.amount ?: 0

    fun getTotalAmount(): Int = items.sumOf { it.amount }
}
