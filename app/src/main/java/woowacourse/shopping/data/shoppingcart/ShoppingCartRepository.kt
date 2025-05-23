package woowacourse.shopping.data.shoppingcart

import woowacourse.shopping.domain.ShoppingProduct

interface ShoppingCartRepository {
    fun insert(productId: Long)

    fun get(productId: Long): ShoppingProduct?

    fun getAll(): List<ShoppingProduct>

    fun getAllSize(): Int

    fun addProduct(productId: Long)

    fun removeProduct(productId: Long)

    fun getQuantity(productId: Long): Int

    fun getPaged(
        limit: Int,
        offset: Int,
    ): List<ShoppingProduct>

    fun delete(productId: Long)
}
