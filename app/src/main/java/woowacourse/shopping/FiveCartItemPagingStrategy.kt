package woowacourse.shopping

import kotlin.math.min

class FiveCartItemPagingStrategy<Product> : PagingStrategy<Product> {
    override fun loadPagedData(
        startPage: Int,
        items: List<Product>,
    ): List<Product> {
        val endOffset = min((startPage) * COUNT_PER_LOAD, items.size)
        return items.subList(fromIndex = (startPage - 1) * COUNT_PER_LOAD, toIndex = endOffset)
    }

    override fun isFinalPage(
        currentPage: Int,
        items: List<Product>,
    ): Boolean = items.size <= (currentPage * COUNT_PER_LOAD)

    companion object {
        private const val COUNT_PER_LOAD = 5
    }
}
