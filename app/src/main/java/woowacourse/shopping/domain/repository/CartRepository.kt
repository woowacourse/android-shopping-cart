package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun insert(
        product: Product,
        quantity: Int = DEFAULT_QUANTITY,
    )

    fun update(
        productId: Long,
        quantity: Int,
    )

    fun itemCount(): Int

    fun totalQuantity(): Int

    fun productQuantity(productId: Long): Int

    fun findOrNullWithProductId(productId: Long): CartItem?

    fun find(cartItemId: Long): CartItem

    fun findAll(): List<CartItem>

    fun findAllPagedItems(
        page: Int,
        pageSize: Int,
    ): List<CartItem>

    fun delete(cartItemId: Long)

    fun deleteAll()

    companion object {
        const val DEFAULT_QUANTITY = 1
    }
}
