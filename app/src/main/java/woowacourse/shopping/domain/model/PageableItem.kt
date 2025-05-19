package woowacourse.shopping.domain.model

data class PageableItem<T>(
    val items: List<T>,
    val hasMore: Boolean,
)
