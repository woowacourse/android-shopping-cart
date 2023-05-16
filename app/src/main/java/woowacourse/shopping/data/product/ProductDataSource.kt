package woowacourse.shopping.data.product

import woowacourse.shopping.domain.Products

interface ProductDataSource {
    fun getByRange(start: Int, range: Int): Products
}
