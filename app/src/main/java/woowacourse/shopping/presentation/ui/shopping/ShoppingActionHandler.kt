package woowacourse.shopping.presentation.ui.shopping

import woowacourse.shopping.presentation.base.CartCountHandler

interface ShoppingActionHandler : CartCountHandler {
    fun onProductClick(productId: Long)

    fun onCartClick()

    fun loadMore()
}
