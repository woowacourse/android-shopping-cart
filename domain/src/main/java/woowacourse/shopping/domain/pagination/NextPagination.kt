package woowacourse.shopping.domain.pagination

interface NextPagination<T> {
    val isNextEnabled: Boolean
        get() = nextItemExist()

    fun nextItems(): List<T>

    fun nextItemExist(): Boolean
}
