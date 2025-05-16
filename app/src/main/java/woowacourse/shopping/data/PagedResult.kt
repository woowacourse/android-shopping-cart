package woowacourse.shopping.data

data class PagedResult<T>(
    val items: List<T>,
    val hasNext: Boolean,
)
