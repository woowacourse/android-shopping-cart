package woowacourse.shopping.model

@JvmInline
value class ProductTitle(
    private val value: String,
) {
    init {
        require(value.trim().isNotEmpty()) { "상품의 제목은 비어있을 수 없습니다." }
    }

    override fun toString(): String = value
}
