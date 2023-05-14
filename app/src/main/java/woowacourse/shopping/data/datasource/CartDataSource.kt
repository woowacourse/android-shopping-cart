package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct

interface CartDataSource {
    fun insertCartProduct(cartProduct: CartProduct)
    fun selectAllCount(): Int
    fun selectAll(): Cart
    fun selectPage(page: Int, countPerPage: Int): Cart
    fun deleteCartProductByOrdinal(ordinal: Int)
}
