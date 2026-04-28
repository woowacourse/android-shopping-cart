package woowacourse.shopping.domain.product

@JvmInline
value class Price(
    private val value: Int,
) {
    init {
        require(value >= 0) { "가격이 음수입니다." }
    }
}
