package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.model.ShoppingCart

interface CartRepository {
    fun insert(productWithQuantity: ProductWithQuantity)

    fun getQuantityByProductId(productId: Long): Result<Int>

    fun plusQuantity(
        productId: Long,
        quantity: Int = 1,
    )

    fun minusQuantity(
        productId: Long,
        quantity: Int = 1,
    )

    fun size(): Int

    fun sumQuantity(): Int

    fun findWithProductId(productId: Long): Result<CartItem>

    fun findCartItemsByPage(
        page: Int,
        pageSize: Int,
    ): Result<ShoppingCart>

    fun deleteByProductId(productId: Long)
}
