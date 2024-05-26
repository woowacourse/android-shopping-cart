package woowacourse.shopping.model

data class Quantity(val count: Int = DEFAULT_VALUE) {
    init {
        require(count >= MIN_VALUE) { INVALID_MIN_VALUE_MESSAGE }
    }

    operator fun inc(): Quantity {
        if (count == MAX_VALUE) return this
        return Quantity(count + 1)
    }

    operator fun dec(): Quantity {
        if (count == MIN_VALUE) return this
        return Quantity(count - 1)
    }

    fun isMin() = count == MIN_VALUE

    fun isGreaterThanMin() = count > MIN_VALUE

    companion object {
        private const val DEFAULT_VALUE = 0
        private const val MAX_VALUE = 99
        private const val MIN_VALUE = 0

        private const val INVALID_MIN_VALUE_MESSAGE = "개수는 $MIN_VALUE 보다 작을 수 없습니다."
    }
}
