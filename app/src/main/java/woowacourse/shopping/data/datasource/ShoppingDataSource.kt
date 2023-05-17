package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.CartProducts

interface ShoppingDataSource {
    fun selectByRange(start: Int, range: Int): CartProducts
    fun initMockData()
}
