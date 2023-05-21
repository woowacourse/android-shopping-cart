package woowacourse.shopping.data.model

typealias DataPage = Page

data class Page(val value: Int, val sizePerPage: Int) {
    val start = value * sizePerPage - sizePerPage
    val end = value * sizePerPage + 1
}
