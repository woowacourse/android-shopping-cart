package woowacourse.shopping.model

class Quantity(private val count: Int) {
    operator fun inc(): Quantity {
        if (count == MAX_VALUE) return this
        return Quantity(count + 1)
    }

    operator fun dec(): Quantity {
        if (count == MIN_VALUE) return this
        return Quantity(count - 1)
    }

    companion object {
        private const val MAX_VALUE = 99
        private const val MIN_VALUE = 0
    }
}
