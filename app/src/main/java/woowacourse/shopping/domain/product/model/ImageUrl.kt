package woowacourse.shopping.domain.product.model

@JvmInline
value class ImageUrl(
    val value: String,
) {
    init {
        require(isValueValid(value)) { "이미지 링크가 비어있습니다." }
    }

    companion object {
        fun isValueValid(value: String) = value.isNotBlank()
    }
}
