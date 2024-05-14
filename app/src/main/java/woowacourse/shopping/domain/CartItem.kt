package woowacourse.shopping.domain

data class CartItem(
    val id: Long,
    val productId: Long,
    val quantity: Int = 1,
) {
    init {
        require(quantity >= MINIMUM_QUANTITY) { EXCEPTION_QUANTITY }
    }

    companion object {
        private const val MINIMUM_QUANTITY = 1
        private const val EXCEPTION_QUANTITY = "카트 아이템 수량은 최소 ${MINIMUM_QUANTITY}개 이상이어야 한다."
    }
}
