package model

@JvmInline
value class Count(val value: Int = 1) {

    init {
        require(value > MINIMUM_COUNT) {
            MINIMUM_COUNT_ERROR
        }
    }

    fun plus() = Count(value + 1)

    fun minus(): Count {
        if (value == 1) {
            return this
        }
        return Count(value - 1)
    }

    companion object {
        private const val MINIMUM_COUNT_ERROR = "상품의 개수는 0보다 커야합니다."
        private const val MINIMUM_COUNT = 0
    }
}
