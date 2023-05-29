package woowacourse.shopping.domain

@JvmInline
value class Count(val value: Int) {
    init {
        validateValue()
    }

    private fun validateValue() {
        require(value >= 0) { COUNT_NEGATIVE_ERROR }
    }

    fun isZero(): Boolean = value == 0

    operator fun plus(otherCount: Count) = Count(value + otherCount.value)
    operator fun minus(otherCount: Count) = Count(value - otherCount.value)

    companion object {
        private const val COUNT_NEGATIVE_ERROR = "상품 갯수는 음수일 수 없습니다."
    }
}
