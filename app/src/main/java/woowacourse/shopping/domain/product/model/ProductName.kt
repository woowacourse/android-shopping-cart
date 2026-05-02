package woowacourse.shopping.domain.product.model

@JvmInline
value class ProductName(
    val value: String,
) {
    init {
        require(value.isNotBlank()) { "상품 이름이 비어있습니다." }
    }
}
