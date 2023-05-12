package woowacourse.shopping.view.cart

import woowacourse.shopping.Pagination
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CartRepository

class CartPagination(private val rangeSize: Int, private val cartRepository: CartRepository) :
    Pagination<CartProduct> {
    override var mark = 0
    override var isNextEnabled = nextItemExist()

    val isNextItemsEnabled: Boolean
        get() = nextItemExist()
    val isUndoItemsEnabled: Boolean
        get() = prevItemExist()

    override fun nextItems(): List<CartProduct> {
        if (nextItemExist()) {
            val items = cartRepository.findRange(mark, rangeSize)
            mark += rangeSize
            return items
        }
        return emptyList()
    }

    fun prevItems(): List<CartProduct> {
        if (prevItemExist()) {
            mark -= rangeSize
            return cartRepository.findRange(mark - rangeSize, rangeSize)
        }
        return emptyList()
    }

    override fun nextItemExist(): Boolean {
        return cartRepository.isExistByMark(mark)
    }
    private fun prevItemExist(): Boolean {
        return cartRepository.isExistByMark(mark - rangeSize - 1)
    }
}
