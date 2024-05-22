package woowacourse.shopping.domain

data class ImageUrl(val url: String) {
    fun isNone(): Boolean = url.isBlank()
}
