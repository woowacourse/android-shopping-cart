package woowacourse.shopping.model

typealias UiPageNumber = PageNumber

data class PageNumber(val value: Int) {
    fun toText(): String = value.toString()
}
