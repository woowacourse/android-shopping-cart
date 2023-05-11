package woowacourse.shopping.view.productlist

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository

class Pagination(private val rangeSize: Int, private val productRepository: ProductRepository) {
    private var mark = 0
    private var isEnabled = nextItemExist()

    fun nextItems(): List<Product> {
        if (isEnabled) {
            val items = productRepository.findRange(mark, rangeSize)
            mark += rangeSize
            isEnabled = nextItemExist()
            return items
        }
        return emptyList()
    }

    private fun nextItemExist(): Boolean {
        return productRepository.isExistByMark(mark)
    }
}
