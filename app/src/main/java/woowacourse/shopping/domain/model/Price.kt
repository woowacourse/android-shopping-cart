package woowacourse.shopping.domain.model

@JvmInline
value class Price(
    val value: Int,
) {
    init {
        require(value >= 0) { "상품 금액은 최소 0원입니다." }
    }
}
