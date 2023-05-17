package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.Cart

interface ShoppingDataSource {
    fun selectByRange(start: Int, range: Int): Cart
    fun initMockData()
}
