package woowacourse.shopping.domain

data class Page<T>(
    val items: List<T>,
    val hasPrevious: Boolean,
    val hasNext: Boolean,
    val pageIndex: Int,
)
