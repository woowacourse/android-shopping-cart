package woowacourse.shopping.domain

@JvmInline
value class Price(val value: Int) {
    init {
        require(value >= MIN_PRICE) {
            "$value - 가격은 ${MIN_PRICE}원 이상 이어야 합니다."
        }
    }

    operator fun times(quantity: Int): Price {
        return Price(value * quantity)
    }

    companion object {
        private const val MIN_PRICE = 0
    }
}
