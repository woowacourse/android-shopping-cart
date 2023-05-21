package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.datasource.entity.ProductEntity

class ProductRepository(private val dataSource: ProductDataSource) {
    fun selectByRange(start: Int, range: Int): List<ProductEntity> {
        return dataSource.selectByRange(start, range)
    }

    fun initMockData() {
        dataSource.initMockData()
    }
}
