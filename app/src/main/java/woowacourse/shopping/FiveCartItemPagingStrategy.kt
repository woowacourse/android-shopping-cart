package woowacourse.shopping

import kotlin.math.min

class FiveCartItemPagingStrategy<Product> : PagingStrategy<Product> {
    override fun loadPagedData(
        startPage: Int,
        items: List<Product>,
    ): List<Product> {
        val endPosition = min((startPage + 1) * COUNT_PER_LOAD, items.size)
        return items.subList(fromIndex = startPage * COUNT_PER_LOAD, toIndex = endPosition)
    }

    companion object {
        private const val COUNT_PER_LOAD = 5
    }
}
