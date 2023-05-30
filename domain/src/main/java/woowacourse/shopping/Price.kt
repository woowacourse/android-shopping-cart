package woowacourse.shopping

data class Price(val value: Int) {
    init {
        require(value >= 0) { PRICE_ERROR_MESSAGE }
    }

    companion object {
        private const val PRICE_ERROR_MESSAGE = "가격은 0원 미만이 될 수 없다."
    }
}
