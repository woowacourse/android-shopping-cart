package woowacourse.shopping.presentation.ui

import woowacourse.shopping.domain.ProductListItem

interface QuantityHandler {
    fun onDecreaseQuantity(item: ProductListItem.ShoppingProductItem?)

    fun onIncreaseQuantity(item: ProductListItem.ShoppingProductItem?)
}
