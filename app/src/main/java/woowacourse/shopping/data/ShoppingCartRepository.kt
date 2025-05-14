package woowacourse.shopping.data

import woowacourse.shopping.domain.ShoppingProduct

interface ShoppingCartRepository {
    fun insert(productId: Long)

    fun getAll(): List<ShoppingProduct>

    fun delete(shoppingCartId: Long)
}
