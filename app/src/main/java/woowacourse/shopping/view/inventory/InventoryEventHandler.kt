package woowacourse.shopping.view.inventory

import woowacourse.shopping.view.inventory.item.InventoryItem.ProductItem

interface InventoryEventHandler {
    fun onSelectProduct(product: ProductItem)

    fun onIncreaseQuantity(
        position: Int,
        product: ProductItem,
    )

    fun onDecreaseQuantity(
        position: Int,
        product: ProductItem,
    )

    fun onShowMore()
}
