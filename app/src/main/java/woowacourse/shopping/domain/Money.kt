package woowacourse.shopping.domain

class Money(
    val amount: Int,
) {
    init {
        require(amount >= 0) { "가격은 0원 이상이어야 합니다." }
    }

    operator fun plus(other: Money): Money {
        return Money(amount + other.amount)
    }

    operator fun minus(other: Money): Money {
        return Money(amount - other.amount)
    }

    operator fun times(other: Int): Money {
        return Money(amount * other)
    }
}
