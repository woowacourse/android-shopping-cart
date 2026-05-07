package woowacourse.shopping.domain

data class CartItem(
    val product: Product,
    val amount: Int,
) {
    val totalPrice: Long = product.price * amount.toLong()

    init {
        require(amount > 0) { "상품 갯수는 1개 이상이어야 합니다." }
    }

    fun addQuantity(amount: Int): CartItem = this.copy(amount = this@CartItem.amount + amount)
}
