package woowacourse.shopping.core.model

@JvmInline
value class Money(
    val amount: Int,
) {
    init {
        require(amount >= 0) { "금액은 0원 이상이어야 합니다." }
    }
}
