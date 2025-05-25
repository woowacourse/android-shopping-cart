package woowacourse.shopping.data.model

data class PageableResponse<T>(
    val items: List<T>,
    val hasMore: Boolean,
)
