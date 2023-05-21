package woowacourse.shopping.data.model

typealias DataPage = Page

class Page(value: Int, sizePerPage: Int) {
    val start = value * sizePerPage - sizePerPage
    val end = value * sizePerPage + 1
}
