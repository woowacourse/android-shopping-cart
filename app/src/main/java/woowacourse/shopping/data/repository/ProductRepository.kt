package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.domain.Product

class ProductRepository(private val localDataSource: ProductDataSource) {
    fun selectByRange(start: Int, range: Int): List<Product> {
        return localDataSource.selectAll(start, range)
    }

    fun initMockData() {
        localDataSource.initMockData()
    }
}
