package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartSinglePage

interface CartRepository {
    operator fun get(id: Long): Cart?

    fun insert(
        productId: Long,
        quantity: Int = 1,
    )

    fun delete(id: Long)

    fun modifyQuantity(
        productId: Long,
        quantity: Quantity,
    )

    fun loadSinglePage(
        page: Int,
        pageSize: Int,
    ): CartSinglePage
}
