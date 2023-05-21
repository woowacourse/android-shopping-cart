package woowacourse.shopping.domain.model

@JvmInline
value class Price(val price: Int) {
    init {
        require(price >= 0) { PRICE_RANGE_ERROR }
    }

    operator fun plus(operand: Price): Price = Price(price + operand.price)

    operator fun minus(operand: Price): Price = Price(price - operand.price)

    operator fun times(count: Int): Price = Price(price * count)

    operator fun plus(operand: Int): Price = Price(price + operand)


    companion object {
        private const val PRICE_RANGE_ERROR = "가격은 0 이상의 숫자이어야합니다."
    }
}
