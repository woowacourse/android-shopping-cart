package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun addCartItem(
        product: Product,
        quantity: Int,
    ): Long

    fun loadPagingCartItems(
        offset: Int,
        pagingSize: Int,
    ): List<CartItem>

    fun deleteCartItem(itemId: Long)

    fun hasNextCartItemPage(
        currentPage: Int,
        itemsPerPage: Int,
    ): Boolean

    fun findCartItemWithProductId(productId: Long): CartItem?

    fun updateCartItem(updatedItem: CartItem)

    fun loadAllCartItems(): List<CartItem>
}
