package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product

interface ShoppingRepository {
    fun findProductInfoById(id: Long): Result<Product>

    fun loadProducts(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<CartItem>>) -> Unit,
    )

    fun loadCartItems(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<CartItem>>) -> Unit,
    )

    fun findQuantityByProductId(
        productId: Long,
        onResult: (Result<Int>) -> Unit,
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
}
