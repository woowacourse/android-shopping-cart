package woowacourse.shopping.data.db.cart

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun save(
        product: Product,
        quantity: Int = DEFAULT_QUANTITY,
    )

    fun update(
        productId: Long,
        quantity: Int,
    )

    fun cartItemSize(): Int

    fun productQuantity(productId: Long): Int

    fun findOrNullByProductId(productId: Long): CartItem?

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
