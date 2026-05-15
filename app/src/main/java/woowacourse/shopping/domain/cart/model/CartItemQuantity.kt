package woowacourse.shopping.domain.cart.model

@JvmInline
value class CartItemQuantity(
    val value: Int,
) {
    init {
        require(isValueValid(value)) { "상품 개수는 0보다 작을 수 없습니다." }
    }

    companion object {
        fun isValueValid(value: Int) = value >= 0
    }
}
