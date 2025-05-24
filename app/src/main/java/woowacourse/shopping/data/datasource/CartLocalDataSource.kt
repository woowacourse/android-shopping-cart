package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartEntity

interface CartLocalDataSource {
    fun getAll(): List<CartEntity>

    fun getTotalQuantity(): Int

    fun loadCartItems(
        offset: Int,
        limit: Int,
    ): List<CartEntity>

    fun existsItemCreatedAfter(createdAt: Long): Boolean

    fun findCartItemByProductId(productId: Long): CartEntity

    fun addCartItem(
        productId: Long,
        increaseQuantity: Int,
    )

    fun decreaseCartItemQuantity(productId: Long)

    fun deleteCartItem(productId: Long)
}
