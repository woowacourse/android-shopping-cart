package woowacourse.shopping.view.cart

import woowacourse.shopping.common.Pagination
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CartRepository

class CartPagination(private val rangeSize: Int, private val cartRepository: CartRepository) :
    Pagination<CartProduct> {
    override var mark = 0
    override var isNextEnabled = nextItemExist()

    val isNextItemsEnabled: Boolean
        get() = nextItemExist()
    val isUndoItemsEnabled: Boolean
        get() = undoItemExist()

    override fun nextItems(): List<CartProduct> {
        if (nextItemExist()) {
            val items = cartRepository.findRange(mark, rangeSize)
            mark += rangeSize
            return items
        }
        return emptyList()
    }

    fun undoItems(): List<CartProduct> {
        if (undoItemExist()) {
            mark -= rangeSize
            val items = cartRepository.findRange(mark - rangeSize, rangeSize)
            return items
        }
        return emptyList()
    }

    override fun nextItemExist(): Boolean {
        return cartRepository.isExistByMark(mark)
    }
    private fun undoItemExist(): Boolean {
        return cartRepository.isExistByMark(mark - rangeSize - 1)
    }

    fun currentItems(): List<CartProduct> {
        val currentMark = mark - rangeSize
        return cartRepository.findRange(currentMark, rangeSize)
    }

    fun getPageNumber(): String {
        val pageNumber = mark / rangeSize
        return pageNumber.toString()
    }
}
