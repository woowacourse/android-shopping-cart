package woowacourse.shopping.model

data class CartItem(
    val productId: ProductId,
    val quantity: Int,
) {
    init {
        require(quantity > 0) { "장바구니 수량은 1개 이상이어야 합니다." }
    }

    fun increase(): CartItem = copy(quantity = quantity + 1)

    fun decreaseOrNull(): CartItem? = if (quantity == 1) null else copy(quantity = quantity - 1)
}
