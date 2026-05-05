package woowacourse.shopping.domain.product.model

@JvmInline
value class Price(
    val value: Int,
) {
    init {
        require(isValueValid(value)) { "가격이 음수입니다." }
    }

    companion object {
        fun isValueValid(value: Int) = value >= 0
    }
}
