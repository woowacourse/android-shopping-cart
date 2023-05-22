package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Shop

interface ShopDataSource {
    fun selectByRange(products: List<Product>): Shop
}
