package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun getCartItems(
        limit: Int,
        offset: Int,
        callback: (List<Product>, Boolean) -> Unit,
    )

    fun deleteCartItem(
        id: Long,
        callback: (Long) -> Unit,
    )

    fun addCartItem(
        product: Product,
        callback: () -> (Unit),
    )
}
