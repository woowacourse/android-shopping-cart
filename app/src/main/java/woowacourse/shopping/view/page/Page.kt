package woowacourse.shopping.view.page

data class Page<T>(
    val items: List<T>,
    val hasPrevious: Boolean,
    val hasNext: Boolean,
    val pageIndex: Int,
)
