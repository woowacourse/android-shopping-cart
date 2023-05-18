package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ShoppingDataSource
import woowacourse.shopping.domain.Shop

class ShoppingRepository(private val localDataSource: ShoppingDataSource) {
    fun selectByRange(start: Int, range: Int): Shop {
        return localDataSource.selectByRange(start, range)
    }

    fun initMockData() {
        localDataSource.initMockData()
    }
}
