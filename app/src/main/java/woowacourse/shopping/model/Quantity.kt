package woowacourse.shopping.model

data class Quantity(val value: Int = DEFAULT_COUNT) {
    operator fun inc(): Quantity {
        return Quantity(value + 1)
    }

    operator fun dec(): Quantity {
        val newCount = (value - OFFSET).coerceAtLeast(DEFAULT_COUNT)
        return Quantity(newCount)
    }

    operator fun plus(quantity: Quantity): Quantity {
        return Quantity(value + quantity.value)
    }

    companion object {
        private const val DEFAULT_COUNT = 0
        private const val OFFSET = 1
    }
}
