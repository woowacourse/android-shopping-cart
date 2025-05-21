package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartEntity

interface CartDataSource {
    fun addCartItem(productId: Long): Result<Unit>

    fun decreaseCartItemQuantity(productId: Long): Result<Unit>

    fun deleteCartItem(productId: Long): Result<Unit>

    fun loadCartItems(
        offset: Int,
        limit: Int,
    ): Result<List<CartEntity>>

    fun findQuantityByProductId(productId: Long): Result<Int>

    fun existsItemCreatedAfter(createdAt: Long): Result<Boolean>
}
