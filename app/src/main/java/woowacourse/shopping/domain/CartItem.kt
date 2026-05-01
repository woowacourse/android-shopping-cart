package woowacourse.shopping.domain

data class CartItem(
    val product: Product,
    val quantity: Int,
) {
    val totalPrice = product.price * quantity

    init {
        require(quantity > 0) { "상품 갯수는 1개 이상이어야 합니다." }
    }

    fun addQuantity(amount: Int): CartItem {
        require(amount > 0) { "장바구니 개수 증가는 양수여야 합니다." }

        return this.copy(quantity = quantity + amount)
    }
}
