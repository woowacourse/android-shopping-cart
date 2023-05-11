package woowacourse.shopping.view.productlist

import android.util.Log
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository

class Pagination(private val rangeSize: Int, private val productRepository: ProductRepository) {
    private var mark = 0

    fun nextItems(): List<Product> {
        Log.d("MARK", mark.toString())
        val items = productRepository.findRange(mark, rangeSize)
        mark += rangeSize
        return items
    }

    fun nextItemExist(): Boolean {
        return productRepository.isExistByMark(mark)
    }
}
