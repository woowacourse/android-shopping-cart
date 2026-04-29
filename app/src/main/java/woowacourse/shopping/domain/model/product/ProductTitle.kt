package woowacourse.shopping.domain.model.product

@JvmInline
value class ProductTitle(val value: String) {
    init {
        require(value.isNotBlank()) { "상품명은 빈 값이거나 공백일 수 없습니다." }
    }
}
