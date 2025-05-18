package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartResult

interface CartRepository {
    operator fun get(id: Long): Cart

    fun insert(productId: Long)

    fun delete(id: Long)

    fun loadSinglePage(
        page: Int,
        pageSize: Int,
    ): CartResult
}
