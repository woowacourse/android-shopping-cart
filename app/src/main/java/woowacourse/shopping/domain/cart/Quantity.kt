package woowacourse.shopping.domain.cart

@JvmInline
value class Quantity(
    val value: Int,
) {
    init {
        require(value >= MIN_VALUE) { "수량은 0 이상이어야 합니다. value=$value" }
    }

    val isZero: Boolean
        get() = value == MIN_VALUE

    fun increase(): Quantity = Quantity(value + 1)

    fun decrease(): Quantity = Quantity((value - 1).coerceAtLeast(MIN_VALUE))

    companion object {
        const val MIN_VALUE = 0
        val ONE = Quantity(1)
        val ZERO = Quantity(0)
    }
}
