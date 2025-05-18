package woowacourse.shopping.view.cart

import woowacourse.shopping.view.cart.adapter.CartProductViewHolder

interface ShoppingCartEventHandler : CartProductViewHolder.EventHandler {
    fun loadPreviousProducts()

    fun loadNextProducts()
}
