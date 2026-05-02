package woowacourse.shopping.domain.product.model

@JvmInline
value class ImageUrl(
    val value: String,
) {
    init {
        require(value.isNotBlank()) { "이미지 링크가 비어있습니다." }
    }
}
