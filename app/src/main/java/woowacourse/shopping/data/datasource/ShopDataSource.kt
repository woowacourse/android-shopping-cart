package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.datasource.entity.ProductEntity
import woowacourse.shopping.domain.Shop

interface ShopDataSource {
    fun selectByRange(products: List<ProductEntity>): Shop
}
