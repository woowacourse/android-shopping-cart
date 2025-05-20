package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.domain.model.PageableItem

interface CartDataSource {
    fun loadCartItems(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<CartEntity>>) -> Unit,
    )

    fun addCartItem(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    )

    fun decreaseCartItemQuantity(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    )

    fun deleteCartItem(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    )

    fun findQuantityByProductId(
        productId: Long,
        onResult: (Result<Int>) -> Unit,
    )
}
