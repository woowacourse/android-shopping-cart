package woowacourse.shopping.model

@JvmInline
value class Money(
    val value: Int,
) {
    init {
        require(value >= 0) { "금액은 0 이상이어야 합니다." }
    }

    operator fun times(quantity: Int): Money = Money(this.value * quantity)
}
