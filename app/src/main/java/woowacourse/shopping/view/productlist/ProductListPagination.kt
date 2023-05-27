package woowacourse.shopping.view.productlist

import android.util.Log
import woowacourse.shopping.common.Pagination
import woowacourse.shopping.data.server.ProductService
import woowacourse.shopping.domain.Product

class ProductListPagination(
    private val rangeSize: Int,
    private val productRepository: ProductService,
) :
    Pagination<Product> {
    override var mark = 0
    override var isNextEnabled = nextItemExist()

    override fun nextItems(): List<Product> {
        Log.d("HJHJ", "isNextEnabled")
        if (isNextEnabled) {
            val items = productRepository.findRange(mark, rangeSize)
            Log.d("HJHJ", "dkssud d fdds $items")
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
