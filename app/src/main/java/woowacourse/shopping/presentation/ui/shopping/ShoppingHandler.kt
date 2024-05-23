package woowacourse.shopping.presentation.ui.shopping

import woowacourse.shopping.domain.ProductListItem

interface ShoppingHandler {
    fun onProductItemClick(productId: Long)

    fun onLoadMoreClick()

    fun onQuantityControlClick(
        item: ProductListItem.ShoppingProductItem?,
        quantityDelta: Int,
    )
}
