package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun loadPagingCartItems(
        offset: Int,
        pagingSize: Int,
    ): List<CartItem>

    fun deleteCartItem(itemId: Long)

    fun hasNextCartItemPage(
        currentPage: Int,
        itemsPerPage: Int,
    ): Boolean

    fun loadAllCartItems(): List<CartItem>

    fun getTotalNumberOfCartItems(): Int

    fun updateIncrementQuantity(
        product: Product,
        incrementAmount: Int,
    ): Int

    fun updateDecrementQuantity(
        product: Product,
        decrementAmount: Int,
        allowZero: Boolean,
    ): Int
}
