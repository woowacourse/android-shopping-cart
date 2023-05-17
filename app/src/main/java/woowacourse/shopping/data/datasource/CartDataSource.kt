package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartOrdinalProduct

interface CartDataSource {
    fun insertCartProduct(cartOrdinalProduct: CartOrdinalProduct)
    fun selectAllCount(): Int
    fun selectAll(): Cart
    fun selectPage(page: Int, countPerPage: Int): Cart
    fun deleteCartProductByOrdinal(ordinal: Int)
}
