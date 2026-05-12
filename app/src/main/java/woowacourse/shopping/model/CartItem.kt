package woowacourse.shopping.model

data class CartItem(
    val product: Product,
    val quantity: Int,
) {
    init {
        require(quantity > 0) { "수량은 1개 이상이어야 합니다." }
    }

    fun increaseQuantity(): CartItem = copy(quantity = quantity + 1)

    fun decreaseQuantity(): CartItem = copy(quantity = quantity - 1)

    fun getTotalPrice(): Money = product.price * quantity
}
