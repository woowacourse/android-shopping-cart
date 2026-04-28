package woowacourse.shopping.core.model

@JvmInline
value class ProductName(
    val name: String,
) {
    init {
        require(name.isNotBlank()) { "상품 이름은 공백일 수 없습니다." }
    }
}
