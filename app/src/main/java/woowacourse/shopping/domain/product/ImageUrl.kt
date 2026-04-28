package woowacourse.shopping.domain.product

@JvmInline
value class ImageUrl(
    private val value: String,
) {
    init {
        require(value.isNotBlank()) { "이미지 링크가 비어있습니다." }
    }
}
