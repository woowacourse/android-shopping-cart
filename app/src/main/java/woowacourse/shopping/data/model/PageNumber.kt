package woowacourse.shopping.data.model

typealias DataPageNumber = PageNumber

data class PageNumber(val value: Int, val sizePerPage: Int) {
    val start = value * sizePerPage - sizePerPage
    val end = value * sizePerPage + 1
}
