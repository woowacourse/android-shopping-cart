package woowacourse.shopping.view

data class PagedResult<T>(
    val items: List<T>,
    val hasNext: Boolean,
)
