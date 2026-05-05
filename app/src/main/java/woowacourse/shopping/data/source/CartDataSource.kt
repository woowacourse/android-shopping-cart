package woowacourse.shopping.data.source

import woowacourse.shopping.domain.CartItem

interface CartDataSource {
    val items: List<CartItem>

    fun add(cartItem: CartItem)

    fun deleteItem(id: String)
}
