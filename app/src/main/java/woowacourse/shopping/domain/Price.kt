package woowacourse.shopping.domain

@JvmInline
value class Price(
    val value: Int,
) {
    init {
        require(value >= MINIMUM_PRICE) { "가격은 최소 0원입니다." }
    }

    companion object {
        private const val MINIMUM_PRICE = 0
    }
}
