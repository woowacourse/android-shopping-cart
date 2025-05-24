package woowacourse.shopping.view.detail

import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct

interface ProductDetailEventHandler {
    fun onAddToCartSelected(product: InventoryProduct)
}
