package woowacourse.shopping.data.cart.server

import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Carts

interface CartService {
    fun getAll(): Carts

    fun getCartById(id: Long): Cart?

    fun getPagedCarts(
        limit: Int,
        offset: Int,
    ): Carts
}
