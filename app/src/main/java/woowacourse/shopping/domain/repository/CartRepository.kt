package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ShoppingCart

interface CartRepository {
    fun insert(
        product: Product,
        quantity: Int,
    )

    fun update(
        productId: Long,
        quantity: Int,
    )

    fun updateQuantity(
        cartItemId: Long,
        quantity: Int,
    )

    fun updateQuantityWithProductId(
        productId: Long,
        quantity: Int,
    )

    fun findQuantityWithProductId(productId: Long): Int

    fun size(): Int

    fun sumOfQuantity(): Int

    fun findOrNullWithProductId(productId: Long): CartItem?

    fun findWithCartItemId(cartItemId: Long): CartItem

    fun findAll(): ShoppingCart

    fun findAllPagedItems(
        page: Int,
        pageSize: Int,
    ): ShoppingCart

    fun delete(cartItemId: Long)

    fun deleteWithProductId(productId: Long)

    fun deleteAll()
}
