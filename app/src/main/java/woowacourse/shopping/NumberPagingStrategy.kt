package woowacourse.shopping

import kotlin.math.min

class NumberPagingStrategy<T>(private val countPerLoad: Int) : PagingStrategy<T> {
    override fun loadPagedData(
        startPage: Int,
        items: List<T>,
    ): List<T> {
        val endOffset = min((startPage) * countPerLoad, items.size)
        return items.subList(fromIndex = (startPage - 1) * countPerLoad, toIndex = endOffset)
    }

    override fun isFinalPage(
        currentPage: Int,
        items: List<T>,
    ): Boolean = items.size <= (currentPage * countPerLoad)
}

class NonePagingStrategy<T> : PagingStrategy<T> {
    override fun loadPagedData(
        startPage: Int,
        items: List<T>,
    ): List<T> = items

    override fun isFinalPage(
        currentPage: Int,
        items: List<T>,
    ): Boolean = true
}
