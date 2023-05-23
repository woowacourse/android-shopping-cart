package woowacourse.shopping.domain.model

data class Price(val value: Int) {
    init {
        validateValue()
    }

    private fun validateValue() {
        require(value >= 0) { PRICE_NEGATIVE_ERROR }
    }

    companion object {
        private const val PRICE_NEGATIVE_ERROR = "상품가격은 음수일 수 없습니다."
    }
}
