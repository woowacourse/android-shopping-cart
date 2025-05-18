package woowacourse.shopping.view.detail

import woowacourse.shopping.view.inventory.item.InventoryItem

interface ProductDetailEventHandler {
    fun onAddToCartSelected(product: InventoryItem.ProductUiModel)
}
