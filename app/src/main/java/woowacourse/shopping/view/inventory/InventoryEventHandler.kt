package woowacourse.shopping.view.inventory

import woowacourse.shopping.view.inventory.item.InventoryItem.ProductItem

interface InventoryEventHandler {
    fun onProductSelected(item: ProductItem)

    fun onIncreaseQuantity(
        position: Int,
        item: ProductItem,
    )

    fun onDecreaseQuantity(
        position: Int,
        item: ProductItem,
    )

    fun onLoadMoreProducts()
}
