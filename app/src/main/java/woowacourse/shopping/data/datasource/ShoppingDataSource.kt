package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.Shop

interface ShoppingDataSource {
    fun selectByRange(start: Int, range: Int): Shop
    fun initMockData()
}
