package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.Shop

interface ShopDataSource {
    fun selectByRange(start: Int, range: Int): Shop
}