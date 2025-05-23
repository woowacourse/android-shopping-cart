package woowacourse.shopping.data.shoppingcart

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Page

interface ShoppingCartRepository2 {
    fun getOrNull(
        id: Int,
        onResult: (CartItem?) -> Unit,
    )

    fun getAll(onSuccess: (List<CartItem>) -> Unit)

    fun getPage(
        pageSize: Int,
        pageIndex: Int,
        onSuccess: (Page<CartItem>) -> Unit,
    )

    fun insert(cartItem: CartItem)

    fun delete(cartItem: CartItem)
}
