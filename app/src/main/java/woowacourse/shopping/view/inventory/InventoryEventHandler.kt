package woowacourse.shopping.view.inventory

import woowacourse.shopping.view.inventory.adapter.InventoryItem.ProductUiModel

interface InventoryEventHandler {
    fun onSelectProduct(productId: Int)

    fun onIncreaseQuantity(product: ProductUiModel)

    fun onDecreaseQuantity(product: ProductUiModel)

    fun onShowMore()
}
