package woowacourse.shopping.domain.product

@JvmInline
value class ProductName(
    private val value: String,
) {
    init {
        require(value.isNotBlank()) { "상품 이름이 비어있습니다." }
    }
}
