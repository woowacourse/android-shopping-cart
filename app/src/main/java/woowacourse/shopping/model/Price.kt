package woowacourse.shopping.model

@JvmInline
value class Price(
    val value: Int,
) {
    init {
        require(value >= 0) { "가격은 음수일 수 없습니다" }
    }
}
