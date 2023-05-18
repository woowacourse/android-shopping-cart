package woowacourse.shopping.domain

interface NextPagination<T> {
    val isNextEnabled: Boolean
        get() = nextItemExist()

    fun nextItems(): List<T>

    fun nextItemExist(): Boolean
}
