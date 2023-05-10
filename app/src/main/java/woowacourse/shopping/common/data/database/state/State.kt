package woowacourse.shopping.common.data.database.state

interface State<T> {
    fun save(t: T)
    fun load(): T
}
