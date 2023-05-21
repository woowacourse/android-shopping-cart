package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ShopDataSource
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Shop

class ShopRepository(private val dataSource: ShopDataSource) {
    fun selectByProducts(products: List<Product>): Shop {
        return dataSource.selectByRange(products)
    }
}
