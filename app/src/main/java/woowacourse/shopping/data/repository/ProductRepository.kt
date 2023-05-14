package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.domain.Products

class ProductRepository(private val localDataSource: ProductDataSource) {
    fun selectByRange(start: Int, range: Int): Products {
        return localDataSource.selectByRange(start, range)
    }

    fun initMockData() {
        localDataSource.initMockData()
    }
}
