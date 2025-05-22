package woowacourse.shopping.data.shoppingcart

import woowacourse.shopping.domain.ShoppingProduct

interface ShoppingCartRepository {
    fun insert(productId: Long)

    fun get(productId: Long): ShoppingProduct?

    fun getAll(): List<ShoppingProduct>

    fun getAllSize(): Int

    fun addProduct(productId: Long)

    fun removeProduct(shoppingCart: ShoppingProduct)

    fun getPaged(
        limit: Int,
        offset: Int,
    ): List<ShoppingProduct>

    fun delete(shoppingCartId: Long)
}
