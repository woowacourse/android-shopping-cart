package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.Product

interface ProductDataSource {
    fun selectAll(start: Int, range: Int): List<Product>
    fun initMockData()
}
