package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ShopDataSource
import woowacourse.shopping.data.datasource.entity.ProductEntity
import woowacourse.shopping.domain.Shop

class ShopRepository(private val dataSource: ShopDataSource) {
    fun selectByRange(products: List<ProductEntity>): Shop {
        return dataSource.selectByRange(products)
    }
}
