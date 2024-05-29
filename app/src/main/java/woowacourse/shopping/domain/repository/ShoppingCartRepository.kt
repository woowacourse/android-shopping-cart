package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.CartItemResult
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.UpdateCartItemResult
import woowacourse.shopping.domain.model.UpdateCartItemType

interface ShoppingCartRepository {
    fun addCartItem(product: Product)

    fun loadPagingCartItems(
        offset: Int,
        pagingSize: Int,
    ): List<CartItem>

    fun deleteCartItem(itemId: Long)

    fun getCartItemResultFromProductId(productId: Long): CartItemResult

    fun updateCartItem(
        productId: Long,
        updateCartItemType: UpdateCartItemType,
    ): UpdateCartItemResult

    fun getTotalCartItemCount(): Int
}
