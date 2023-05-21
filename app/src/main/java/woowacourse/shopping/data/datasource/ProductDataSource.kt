package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.datasource.entity.ProductEntity

interface ProductDataSource {
    fun selectByRange(start: Int, range: Int): List<ProductEntity>
    fun initMockData()
}
