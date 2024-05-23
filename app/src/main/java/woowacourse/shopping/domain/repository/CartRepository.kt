package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.model.ShoppingCart

interface CartRepository {
    fun insert(productWithQuantity: ProductWithQuantity)

    fun getQuantityByProductId(productId: Long): Int?

    fun plusQuantity(
        productId: Long,
        quantity: Int = 1,
    )

    fun minusQuantity(
        productId: Long,
        quantity: Int = 1,
    )

    fun size(): Int

    fun findOrNullWithProductId(productId: Long): CartItem?

    fun sumQuantity(): Int

    fun findCartItemsByPage(
        page: Int,
        pageSize: Int,
    ): ShoppingCart

    fun delete(cartItemId: Long)

    fun deleteAll()
}
