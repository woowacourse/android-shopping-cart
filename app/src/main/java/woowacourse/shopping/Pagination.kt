package woowacourse.shopping

interface Pagination<T> {
    var mark: Int
    var isNextEnabled: Boolean

    fun nextItems(): List<T>

    fun nextItemExist(): Boolean
}
