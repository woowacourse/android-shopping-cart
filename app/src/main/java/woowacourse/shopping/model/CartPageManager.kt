package woowacourse.shopping.model

import kotlin.math.min

class CartPageManager(private val pageSize: Int) {
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
        val fromIndex = (pageNum - OFFSET) * pageSize
        val toIndex = min(fromIndex + pageSize, itemSize)
        return toIndex != itemSize
    }

    companion object {
        private const val OFFSET = 1
        private const val DEFAULT_PAGE_NUM = 1
    }
}
