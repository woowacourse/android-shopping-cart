package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product

interface CartDataSource {
    fun plusCartProduct(product: Product)
    fun minusCartProduct(product: Product)
    fun deleteCartProduct(product: Product)
    fun selectAllCount(): Int
    fun selectAll(): Cart
    fun selectPage(page: Int, countPerPage: Int): Cart
}
