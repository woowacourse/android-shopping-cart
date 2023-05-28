package woowacourse.shopping.view.cart

import woowacourse.shopping.common.Pagination
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CartProductRepository

class CartPagination(private val rangeSize: Int, private val cartProductRepository: CartProductRepository) :
    Pagination<CartProduct> {
    override var mark = 0
    override var isNextEnabled = nextItemExist()

    val isNextItemsEnabled: Boolean
        get() = nextItemExist()
    val isUndoItemsEnabled: Boolean
        get() = undoItemExist()

    override fun nextItems(): List<CartProduct> {
        if (nextItemExist()) {
            val items = cartProductRepository.findRange(mark, rangeSize)
            mark += rangeSize
            return items
        }
        return emptyList()
    }

    fun undoItems(): List<CartProduct> {
        if (undoItemExist()) {
            mark -= rangeSize
            val items = cartProductRepository.findRange(mark - rangeSize, rangeSize)
            return items
        }
        return emptyList()
    }

    override fun nextItemExist(): Boolean {
        return cartProductRepository.isExistByMark(mark)
    }
    private fun undoItemExist(): Boolean {
        return cartProductRepository.isExistByMark(mark - rangeSize - 1)
    }

    fun currentItems(): List<CartProduct> {
        val currentMark = mark - rangeSize
        return cartProductRepository.findRange(currentMark, rangeSize)
    }

    fun getPageNumber(): String {
        val pageNumber = mark / rangeSize
        return pageNumber.toString()
    }

    // 전체 선택
    // 1. 현재 페이지의 전체 리스트 받아오기
    // 2. 받아온 리스트의 id를 이용해 DB의 check 값 false 바꾸기
    // 3.
}
