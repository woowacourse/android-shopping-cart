package woowacourse.shopping.domain

@JvmInline
value class Money(val amount: Long) {
    init {
        require(amount >= 0) { "가격은 0원 이상이어야 합니다." }
    }

    operator fun plus(otherMoney: Money): Money = Money(amount + otherMoney.amount)

    operator fun minus(otherMoney: Money): Money = Money(amount - otherMoney.amount)

    operator fun times(quantity: Quantity): Money = Money(amount * quantity.count)
}
