package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Shop

interface CartDataSource {
    fun plusCartProduct(product: Product)
    fun minusCartProduct(product: Product)
    fun deleteCartProduct(product: Product)
    fun selectAllCount(): Int
    fun selectAll(): Shop
    fun selectPage(page: Int, countPerPage: Int): Shop
}
