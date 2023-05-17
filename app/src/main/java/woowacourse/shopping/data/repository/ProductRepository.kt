package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ShoppingDataSource
import woowacourse.shopping.domain.CartProducts

class ProductRepository(private val localDataSource: ShoppingDataSource) {
    fun selectByRange(start: Int, range: Int): CartProducts {
        return localDataSource.selectByRange(start, range)
    }

    fun initMockData() {
        localDataSource.initMockData()
    }
}
