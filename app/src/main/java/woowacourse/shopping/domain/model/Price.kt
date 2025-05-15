package woowacourse.shopping.domain.model

@JvmInline
value class Price(val value: Int) {
    init {
        require(value > MINIMUM_PRICE) { ERROR_MINIMUM_PRICE }
    }

    companion object {
        private const val MINIMUM_PRICE: Int = 0

        private const val ERROR_MINIMUM_PRICE: String = "가격은 0원 이하일 수 없습니다"
    }
}
