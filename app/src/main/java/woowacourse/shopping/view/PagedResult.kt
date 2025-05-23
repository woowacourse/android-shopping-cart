package woowacourse.shopping.view

data class PagedResult<T>(
    val items: List<T>,
    val hasNext: Boolean,
) {
    operator fun plus(result: PagedResult<T>): PagedResult<T> =
        PagedResult(
            items = this.items + result.items,
            hasNext = this.hasNext && result.hasNext,
        )
}
