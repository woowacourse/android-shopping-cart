package woowacourse.shopping.view.detail

import woowacourse.shopping.view.model.InventoryItem

interface ProductDetailEventHandler {
    fun onAddToCartSelected(product: InventoryItem.ProductUiModel)
}
