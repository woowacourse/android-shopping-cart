package woowacourse.shopping.data.shoppingcart

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.domain.ShoppingProduct

interface ShoppingCartRepository {
    fun insert(productId: Long)

    fun getAll(): List<ShoppingProduct>

    fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<ShoppingProduct>

    fun delete(shoppingCartId: Long)
}
