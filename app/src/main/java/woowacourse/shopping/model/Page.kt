package woowacourse.shopping.model

typealias UiPage = Page

data class Page(val value: Int) {
    fun toText(): String = value.toString()
}
