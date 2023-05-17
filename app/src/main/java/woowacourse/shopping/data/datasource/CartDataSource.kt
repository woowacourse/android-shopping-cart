package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product

interface CartDataSource {
    fun insertCartProduct(product: Product)
    fun selectAllCount(): Int
    fun selectAll(): Cart
    fun selectPage(page: Int, countPerPage: Int): Cart
    fun deleteCartProductByOrdinal(product: Product)
}
