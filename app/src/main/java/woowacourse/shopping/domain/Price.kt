package woowacourse.shopping.domain

@JvmInline
value class Price(
    val value: Long,
) {
    init {
        require(value >= 0L) { "상품 가격이 0원 미만일 수 없습니다." }
    }

    operator fun times(quantity: Int): Price = Price(value * quantity)
}
