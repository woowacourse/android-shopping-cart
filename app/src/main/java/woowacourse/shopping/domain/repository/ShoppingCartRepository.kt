package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.CartItemResult
import woowacourse.shopping.domain.model.Product

interface ShoppingCartRepository {
    fun addCartItem(product: Product)

    fun loadPagingCartItems(
        offset: Int,
        pagingSize: Int,
    ): List<CartItem>

    fun deleteCartItem(itemId: Long)

    fun getCartItemResultFromProductId(productId: Long): CartItemResult

    fun updateCartItem(itemId : Long,count: Int)
}
