package woowacourse.shopping.model

@JvmInline
value class Price(
    private val value: Int,
) {
    init {
        require(value >= 0) { "가격은 음수일 수 없습니다" }
    }

    fun toInt(): Int = value
}
