package woowacourse.shopping.data.cart

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.domain.CartProduct

interface CartProductRepository {
    fun insert(
        productId: Long,
        quantity: Int = 1,
    )

    fun getAll(): List<CartProduct>

    fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<CartProduct>

    fun getQuantityByProductId(productId: Long): Int?

    fun getTotalQuantity(): Int?

    fun updateQuantity(
        productId: Long,
        quantity: Int,
    )

    fun deleteByProductId(productId: Long)
}
