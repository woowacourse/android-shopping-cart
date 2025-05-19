package woowacourse.shopping.domain.model

@JvmInline
value class Price(val value: Int) {
    init {
        require(value >= MIN_PRICE) { INVALID_PRICE_MESSAGE }
    }

    companion object {
        private const val MIN_PRICE = 0
        private const val INVALID_PRICE_MESSAGE = "최소 금액은 $MIN_PRICE 입니다."
    }
}
