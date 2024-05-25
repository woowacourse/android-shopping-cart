package woowacourse.shopping.model

class Quantity(val count: Int = DEFAULT_VALUE) {
    operator fun inc(): Quantity {
        if (count == MAX_VALUE) return this
        return Quantity(count + 1)
    }

    operator fun dec(): Quantity {
        if (count == MIN_VALUE) return this
        return Quantity(count - 1)
    }

    fun isMin() = count == MIN_VALUE

    fun calculate(price: Int) = count * price

    companion object {
        private const val DEFAULT_VALUE = 1
        private const val MAX_VALUE = 99
        private const val MIN_VALUE = 1
    }
}
