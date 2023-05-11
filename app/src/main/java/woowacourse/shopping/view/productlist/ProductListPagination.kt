package woowacourse.shopping.view.productlist

import woowacourse.shopping.Pagination
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository

class ProductListPagination(private val rangeSize: Int, private val productRepository: ProductRepository) :
    Pagination<Product> {
    override var mark = 0
    override var isNextEnabled = nextItemExist()

    override fun nextItems(): List<Product> {
        if (isNextEnabled) {
            val items = productRepository.findRange(mark, rangeSize)
            mark += rangeSize
            isNextEnabled = nextItemExist()
            return items
        }
        return emptyList()
    }

    override fun nextItemExist(): Boolean {
        return productRepository.isExistByMark(mark)
    }
}
