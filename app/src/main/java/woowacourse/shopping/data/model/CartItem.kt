package woowacourse.shopping.data.model

data class CartItem(
    val id: Long = 1,
    val quantity: Int = 1,
    val productId: Long,
) {
    init {
        require(id >= MINIMUM_ID) { EXCEPTION_ILLEGAL_ID }
        require(quantity >= MINIMUM_QUANTITY) { EXCEPTION_ILLEGAL_QUANTITY }
    }

    companion object {
        private const val MINIMUM_QUANTITY = 1
        private const val MINIMUM_ID = 1
        private const val EXCEPTION_ILLEGAL_QUANTITY = "카트 아이템 수량은 최소 ${MINIMUM_QUANTITY}개 이상이어야 한다."
        private const val EXCEPTION_ILLEGAL_ID = "카트 아이템 아이디는 최소 ${MINIMUM_ID}이상이어야 한다."
    }
}
