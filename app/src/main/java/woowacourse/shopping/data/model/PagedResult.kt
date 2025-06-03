package woowacourse.shopping.data.model

data class PagedResult<T>(
    val items: List<T>,
    val hasNext: Boolean,
)
