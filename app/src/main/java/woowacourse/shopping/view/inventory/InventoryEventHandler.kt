package woowacourse.shopping.view.inventory

import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct

interface InventoryEventHandler {
    fun onProductSelected(product: InventoryProduct)

    fun onIncreaseQuantity(
        position: Int,
        product: InventoryProduct,
    )

    fun onDecreaseQuantity(
        position: Int,
        product: InventoryProduct,
    )

    fun onLoadMoreProducts()
}
