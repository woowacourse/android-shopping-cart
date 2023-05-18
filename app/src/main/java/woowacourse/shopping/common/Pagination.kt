package woowacourse.shopping.common

interface Pagination<T> {
    var mark: Int
    var isNextEnabled: Boolean

    fun nextItems(): List<T>

    fun nextItemExist(): Boolean
}
