package woowacourse.shopping.model

@JvmInline
value class Money(
    val amount: Int,
) {
    init {
        require(amount >= 0) { "금액은 0원 이상이어야 합니다." }
    }

    operator fun times(time: Int): Money {
        require(time >= 0) { "금액은 0원 이상이어야 합니다." }
        return Money(Math.multiplyExact(amount, time))
    }
}
