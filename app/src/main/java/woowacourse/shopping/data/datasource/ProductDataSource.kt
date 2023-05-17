package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.CartProducts

interface ProductDataSource {
    fun selectByRange(start: Int, range: Int): CartProducts
    fun initMockData()
}
