package woowacourse.shopping.data.state

interface State<T> {
    fun save(t: T)
    fun load(): T
}
