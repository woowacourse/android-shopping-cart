package woowacourse.shopping.view.inventory

import woowacourse.shopping.view.inventory.item.InventoryItem

interface InventoryEventHandler {
    fun onProductSelected(product: InventoryItem.ProductUiModel)

    fun onLoadMoreProducts()
}
