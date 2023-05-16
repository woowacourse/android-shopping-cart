package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import java.time.LocalDateTime

interface CartRepository {
    fun addCartProduct(cartProduct: CartProduct)

    fun getAll(): Cart

    fun getAllCount(): Int

    fun getPage(page: Int, sizePerPage: Int): Cart

    fun deleteCartProductByTime(time: LocalDateTime)
}
