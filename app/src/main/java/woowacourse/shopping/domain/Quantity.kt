package woowacourse.shopping.domain

@JvmInline
value class Quantity(val value: Int) {
    fun hasQuantity() = value > 0

    operator fun plus(other: Int): Quantity {
        return Quantity(this.value + other)
    }

    operator fun minus(other: Int): Quantity {
        return Quantity((this.value - other).coerceAtLeast(0))
    }
}
