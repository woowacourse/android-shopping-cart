package woowacourse.shopping.domain.product

@JvmInline
value class Money(val amount: Int) {
    init {
        require(amount >= 0) { ERROR_INVALID_PRICE }
    }

    companion object {
        private const val ERROR_INVALID_PRICE = "금액은 0 이상 이어야 합니다."
    }
}
