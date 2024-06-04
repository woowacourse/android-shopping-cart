package woowacourse.shopping.presentation.ui.shopping

import woowacourse.shopping.presentation.ui.QuantityHandler

interface ShoppingHandler : QuantityHandler {
    fun onProductItemClick(productId: Long)

    fun onLoadMoreClick()

    fun onCartMenuItemClick()
}
