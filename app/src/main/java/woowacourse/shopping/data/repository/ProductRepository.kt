package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.domain.Product

class ProductRepository(private val dataSource: ProductDataSource) {
    fun selectByRange(start: Int, range: Int): List<Product> {
        return dataSource.selectByRange(start, range)
    }

    fun initMockData() {
        dataSource.initMockData()
    }
}
