package woowacourse.shopping.domain

interface PrevPagination<T> {
    val isPrevEnabled: Boolean
        get() = prevItemExist()

    fun prevItems(): List<T>

    fun prevItemExist(): Boolean
}
