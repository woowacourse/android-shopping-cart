package woowacourse.shopping.presentation.ui

import woowacourse.shopping.domain.ProductListItem

interface QuantityHandler {
    fun onQuantityControlClick(
        item: ProductListItem.ShoppingProductItem?,
        quantityDelta: Int,
    )
}
