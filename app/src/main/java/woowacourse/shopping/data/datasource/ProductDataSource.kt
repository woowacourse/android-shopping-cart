package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.Products

interface ProductDataSource {
    fun selectByRange(start: Int, range: Int): Products
    fun initMockData()
}
