package woowacourse.shopping.view.detail

import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct

interface ProductDetailEventHandler {
    fun onAddToCart(product: InventoryProduct)

    fun onIncreaseQuantity()

    fun onDecreaseQuantity()
}
