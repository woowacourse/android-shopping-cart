package woowacourse.shopping.data.model

typealias DataPageNumber = PageNumber

data class PageNumber(val value: Int, val sizePerPage: Int) {
    val startId = value * sizePerPage - sizePerPage
    val pageSizeForCheckHasNext: Int = sizePerPage + 1
}
