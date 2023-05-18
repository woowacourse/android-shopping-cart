package woowacourse.shopping.domain

@JvmInline
value class Price(val value: Int) {
    init {
        validateValue()
    }

    private fun validateValue() {
        require(value >= 0) { PRICE_NEGATIVE_ERROR }
    }

    operator fun plus(otherPrice: Price) = Price(value + otherPrice.value)
    operator fun minus(otherPrice: Price) = Price(value - otherPrice.value)
    operator fun times(count: Count) = Price(value * count.value)

    companion object {
        private const val PRICE_NEGATIVE_ERROR = "상품가격은 음수일 수 없습니다."
    }
}
