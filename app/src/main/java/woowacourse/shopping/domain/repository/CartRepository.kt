package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun getCartItems(
        limit: Int,
        offset: Int,
        onResult: (Result<PageableItem<CartItem>>) -> Unit,
    )

    fun deleteCartItem(
        id: Long,
        onResult: (Result<Long>) -> Unit,
    )

    fun addCartItem(
        product: Product,
        onResult: (Result<Unit>) -> (Unit),
    )
}
