package woowacourse.shopping.presentation.ui.cart

import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.presentation.ui.QuantityHandler

interface CartHandler : QuantityHandler {
    fun onNextPageClick()

    fun onBeforePageClick()

    fun onDeleteClick(product: ProductListItem.ShoppingProductItem)
}
