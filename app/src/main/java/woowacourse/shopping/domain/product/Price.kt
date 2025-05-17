package woowacourse.shopping.domain.product

@JvmInline
value class Price(val value: Int) {
    init {
        require(value >= 0) { INVALID_VALUE }
    }

    companion object {
        private const val INVALID_VALUE = "가격은 0원 이상이다"
    }
}
