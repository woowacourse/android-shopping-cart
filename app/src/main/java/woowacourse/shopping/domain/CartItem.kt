package woowacourse.shopping.domain

data class CartItem(
    val product: Product,
    val quantity: Int,
) {
    val totalPrice = product.price * quantity

    init {
        require(quantity > 0) { "상품 갯수는 1개 이상이어야 합니다." }
    }
}
