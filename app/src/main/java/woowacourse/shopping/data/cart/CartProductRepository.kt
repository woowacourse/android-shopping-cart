package woowacourse.shopping.data.cart

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.domain.CartProduct

interface CartProductRepository {
    fun insert(productId: Long)

    fun getAll(): List<CartProduct>

    fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<CartProduct>

    fun delete(shoppingCartId: Long)
}
