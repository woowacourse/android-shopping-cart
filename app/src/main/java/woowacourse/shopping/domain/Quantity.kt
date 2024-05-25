package woowacourse.shopping.domain

data class Quantity(val value: Int = MINIMUM_QUANTITY) {
    init {
        require(value >= MINIMUM_QUANTITY) {
            "상품 개수는 $MINIMUM_QUANTITY 이상이어야 합니다: 현재 개수 = $value"
        }
    }

    fun increase(): Quantity = Quantity(value.inc())

    fun decrease(): Quantity = Quantity(value.dec())

    companion object {
        const val MINIMUM_QUANTITY = 1
    }
}
