package woowacourse.shopping.view.cart

import woowacourse.shopping.Pagination
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.model.ProductModel

class CartPagination(private val rangeSize: Int, private val cartRepository: CartRepository) :
    Pagination<CartProduct> {
    override var mark = 0
    override var isNextEnabled = nextItemExist()

    val isNextItemsEnabled: Boolean
        get() = nextItemExist()
    val isUndoItemsEnabled: Boolean
        get() = undoItemExist()

    private var undoItems: List<CartProduct> = emptyList()
    private var currentItems: List<CartProduct> = emptyList()
    private var nextItems: List<CartProduct> = emptyList()

    override fun nextItems(): List<CartProduct> {
        if (currentItems.isEmpty()) { // 첫 실행
            currentItems = cartRepository.findRange(mark, rangeSize)
            mark += rangeSize
            nextItems = if(nextItemExist()) cartRepository.findRange(mark, rangeSize) else emptyList()
            return currentItems
        }
        if(nextItems.isNotEmpty()) { // 첫 실행 이후 && 다음 아이템이 있는 경우
            undoItems = currentItems.toList()
            currentItems = nextItems.toList()
            mark += rangeSize
            nextItems = if(nextItemExist()) cartRepository.findRange(mark, rangeSize) else emptyList()
            return currentItems
        } // 다음 아이템이 없는 경우
        return emptyList()
    }

    fun undoItems(): List<CartProduct> {
        if (undoItems.isNotEmpty()) {
            nextItems = currentItems.toList()
            currentItems = undoItems.toList()
            mark -= rangeSize
            undoItems = if(undoItemExist()) cartRepository.findRange(mark - rangeSize, rangeSize) else emptyList()
            return currentItems
        }
        return emptyList()
    }

    override fun nextItemExist(): Boolean {
        return cartRepository.isExistByMark(mark)
    }
    private fun undoItemExist(): Boolean {
        return cartRepository.isExistByMark(mark - rangeSize)
    }
}
