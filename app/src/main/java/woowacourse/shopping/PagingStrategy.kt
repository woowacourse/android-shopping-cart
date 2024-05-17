package woowacourse.shopping

interface PagingStrategy<T> {
    fun loadPagedData(
        startPage: Int,
        items: List<T>,
    ): List<T>
}
