package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.model.CartItem

interface CartDataSource {
    fun getCartItems(
        page: Int,
        pageSize: Int,
    ): List<CartItem>

    fun addCartItem(cartItem: CartItem): Long
}
