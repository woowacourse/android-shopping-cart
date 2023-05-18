package model

@JvmInline
value class Price(val value: Int) {

    init {
        require(value >= 0) {
            PRICE_AMOUNT_ERROR
        }
    }

    operator fun times(other: Int): Price {

        return Price(value * other)
    }

    companion object {
        private const val PRICE_AMOUNT_ERROR = "가격은 음수 일 수 없습니다."
    }
}
