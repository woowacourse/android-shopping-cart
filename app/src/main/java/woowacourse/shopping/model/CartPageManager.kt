package woowacourse.shopping.model

import kotlin.math.min

class CartPageManager {
    var pageNum: Int = DEFAULT_PAGE_NUM
        private set

    fun plusPageNum() {
        pageNum += OFFSET
    }

    fun minusPageNum() {
        pageNum -= OFFSET
        pageNum = pageNum.coerceAtLeast(DEFAULT_PAGE_NUM)
    }

    fun canMovePreviousPage(): Boolean {
        return pageNum != DEFAULT_PAGE_NUM
    }

    fun canMoveNextPage(itemSize: Int): Boolean {
        val fromIndex = (pageNum - OFFSET) * PAGE_SIZE
        val toIndex = min(fromIndex + PAGE_SIZE, itemSize)
        return toIndex != (itemSize)
    }

    companion object {
        private const val OFFSET = 1
        private const val DEFAULT_PAGE_NUM = 1
        private const val PAGE_SIZE = 5
    }
}
