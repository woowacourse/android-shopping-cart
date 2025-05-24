package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product

interface ShoppingRepository {
    fun getAll(onResult: (Result<List<CartItem>>) -> Unit)

    fun getTotalQuantity(onResult: (Result<Int>) -> Unit)

    fun findProductInfoById(
        id: Long,
        onResult: (Result<Product>) -> Unit,
    )

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
