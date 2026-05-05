package woowacourse.shopping.domain.model

@JvmInline
value class Money(
    val amount: Long,
) {
    init {
        require(amount >= 0) { "금액은 0원 이상이어야 합니다." }
    }

    operator fun times(time: Int): Money = Money(this.amount * time)
}
