package woowacourse.shopping.domain

@JvmInline
value class Price(val price: Int) {
    init {
        require(price >= 0) { PRICE_RANGE_ERROR }
    }

    companion object {
        private const val PRICE_RANGE_ERROR = "가격은 0 이상의 숫자이어야합니다."
    }
}
