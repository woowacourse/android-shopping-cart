package woowacourse.shopping.data.shoppingcart

import woowacourse.shopping.domain.ShoppingProduct
import woowacourse.shopping.view.PagedResult

interface ShoppingCartRepository {
    fun insert(productId: Long)

    fun getAll(): List<ShoppingProduct>

    fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<ShoppingProduct>

    fun delete(shoppingCartId: Long)
}
