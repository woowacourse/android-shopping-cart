package woowacourse.shopping.domain

data class CartItem(
    val productId: String,
    val quantity: Int,
) {
    init {
        require(quantity > 0) { "상품 갯수는 1개 이상이어야 합니다." }
    }
}
