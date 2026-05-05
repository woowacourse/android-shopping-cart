package woowacourse.shopping.domain.product.model

@JvmInline
value class ProductName(
    val value: String,
) {
    init {
        require(isValueValid(value)) { "상품 이름이 비어있습니다." }
    }

    companion object {
        fun isValueValid(value: String) = value.isNotBlank()
    }
}
