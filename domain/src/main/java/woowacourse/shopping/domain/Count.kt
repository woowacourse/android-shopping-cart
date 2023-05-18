package woowacourse.shopping.domain

@JvmInline
value class Count(val value: Int) {
    init {
        validateValue()
    }

    private fun validateValue() {
        require(value >= 0) { COUNT_NEGATIVE_ERROR }
    }

    companion object {
        private const val COUNT_NEGATIVE_ERROR = "상품 갯수는 음수일 수 없습니다."
    }
}
