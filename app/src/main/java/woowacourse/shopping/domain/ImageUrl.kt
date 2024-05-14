package woowacourse.shopping.domain

@JvmInline
value class ImageUrl(val url: String) {
    fun isNone(): Boolean = url.isBlank()
}
