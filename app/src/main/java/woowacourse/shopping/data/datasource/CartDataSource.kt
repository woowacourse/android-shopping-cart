package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartEntity

interface CartDataSource {
    fun getAll(): List<CartEntity>

    fun getCartItemCount(): Int

    fun addCartItem(
        productId: Long,
        increaseQuantity: Int,
    )

    fun decreaseCartItemQuantity(productId: Long)

    fun deleteCartItem(productId: Long)

    fun loadCartItems(
        offset: Int,
        limit: Int,
    ): List<CartEntity>

    fun findCartItemByProductId(productId: Long): CartEntity

    fun findQuantityByProductId(productId: Long): Int

    fun existsItemCreatedAfter(createdAt: Long): Boolean
}
