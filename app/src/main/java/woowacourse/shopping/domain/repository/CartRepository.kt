package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.PageableItem

interface CartRepository {
    fun getAll(onResult: (Result<List<CartProduct>>) -> Unit)

    fun getTotalQuantity(onResult: (Result<Int>) -> Unit)

    fun loadCartItems(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<CartProduct>>) -> Unit,
    )

    fun findCartItemByProductId(
        productId: Long,
        onResult: (Result<CartProduct>) -> Unit,
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
