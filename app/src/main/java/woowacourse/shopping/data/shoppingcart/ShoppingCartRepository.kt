package woowacourse.shopping.data.shoppingcart

import woowacourse.shopping.domain.ShoppingProduct

interface ShoppingCartRepository {
    fun insert(productId: Long)

    fun getAll(): List<ShoppingProduct>

    fun getPaged(
        limit: Int,
        offset: Int,
    ): List<ShoppingProduct>

    fun delete(shoppingCartId: Long)
}
