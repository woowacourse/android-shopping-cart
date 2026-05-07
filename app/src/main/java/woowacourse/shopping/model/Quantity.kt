package woowacourse.shopping.model

@JvmInline
value class Quantity(
    val value: Int,
) {
    init {
        require(value >= 0) { "수량은 음수일 수 없습니다" }
    }
}
