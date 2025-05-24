package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem

interface CartRepository {
    fun getAll(onResult: (Result<List<CartItem>>) -> Unit)

    fun getTotalQuantity(onResult: (Result<Int>) -> Unit)

    fun loadCartItems(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<CartItem>>) -> Unit,
    )

    fun findCartItemByProductId(
        productId: Long,
        onResult: (Result<CartItem>) -> Unit,
    )

    fun findQuantityByProductId(
        productId: Long,
        onResult: (Result<Int>) -> Unit,
    )

    fun addCartItem(
        productId: Long,
        increaseQuantity: Int,
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
}
