package woowacourse.shopping.domain

@JvmInline
value class Price(
    val value: Int,
) {
    init {
        require(value >= 0) { "가격은 0보다 크거나 같아야 합니다." }
    }
}
