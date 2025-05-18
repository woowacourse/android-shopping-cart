package woowacourse.shopping.view.inventory

import woowacourse.shopping.view.model.InventoryItem

interface InventoryEventHandler {
    fun onProductSelected(product: InventoryItem.ProductUiModel)

    fun onLoadMoreProducts()
}
