package woowacourse.shopping.view.cart

import woowacourse.shopping.Pagination
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.model.ProductModel

class CartPagination(private val rangeSize: Int, private val cartRepository: CartRepository) :
    Pagination<CartProduct> {
    override var mark = 0
    override var isNextEnabled = nextItemExist()

    override fun nextItems(): List<CartProduct> {
        if (isNextEnabled) {
            val items = cartRepository.findRange(mark, rangeSize)
            mark += rangeSize
            isNextEnabled = nextItemExist()
            return items
        }
        return emptyList()
    }

    override fun nextItemExist(): Boolean {
        return cartRepository.isExistByMark(mark)
    }
}
